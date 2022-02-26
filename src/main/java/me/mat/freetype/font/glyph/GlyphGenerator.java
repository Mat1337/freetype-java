package me.mat.freetype.font.glyph;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.mat.freetype.FreeType;
import me.mat.freetype.font.face.Face;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GlyphGenerator {

    private final Map<Character, Glyph> glyphMap = new HashMap<>();

    @NonNull
    protected final int padding;

    @NonNull
    protected final int upScaleResolution;

    @NonNull
    protected final int spread;

    protected int fontSize;
    private float fontScale;
    private Face face;

    private GlyphGenerator load(InputStream inputStream, int fontSize) {
        try {
            FreeType.initialize();
            this.load(FreeType.newFace(inputStream), fontSize);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private GlyphGenerator load(File file, int fontSize) {
        try {
            FreeType.initialize();
            this.load(FreeType.newFace(file), fontSize);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void load(Face face, int fontSize) {
        this.face = face;
        this.fontSize = fontSize;
        this.fontScale = this.upScaleResolution / (float) this.fontSize;
        this.face.setPixelSizes(0, fontSize);
    }

    public Glyph getGlyph(char aChar) {
        if (!glyphMap.containsKey(aChar)) {
            return generateGlyph(aChar);
        }
        return glyphMap.get(aChar);
    }

    public void generateGlyphs(char start, char end) {
        if (!glyphMap.isEmpty()) {
            throw new RuntimeException("Glyphs are already generated");
        }
        for (int i = start; i < end; i++) {
            generateGlyph((char) i);
        }
        FreeType.free();
    }

    private Glyph generateGlyph(char aChar) {
        Glyph glyph = createCharGlyph(aChar);
        if (glyph == null) {
            throw new IllegalArgumentException("'" + aChar + "' failed to generate a glyph");
        }
        glyphMap.put(aChar, glyph);
        return glyph;
    }

    Glyph createCharGlyph(char aChar) {
        face.setPixelSizes(0, upScaleResolution);
        if (face.loadChar(aChar, FreeType.LOAD_RENDER)) {
            System.out.println("FreeType could not generate character.");
            return null;
        }

        GlyphSlot glyphSlot = face.getGlyphSlot();
        if (glyphSlot == null) {
            return null;
        }

        int glyphWidth = face.getGlyphSlot().getBitmap().getWidth();
        int glyphHeight = face.getGlyphSlot().getBitmap().getRows();
        byte[] glyphBitmap = new byte[glyphHeight * glyphWidth];
        face.getGlyphSlot().getBitmap().getBuffer().get(glyphBitmap, 0, glyphWidth * glyphHeight);

        float widthScale = (float) glyphWidth / (float) upScaleResolution;
        float heightScale = (float) glyphHeight / (float) upScaleResolution;

        int characterWidth = (int) ((float) fontSize * widthScale);
        int characterHeight = (int) ((float) fontSize * heightScale);

        int bitmapWidth = characterWidth + padding * 2;
        int bitmapHeight = characterHeight + padding * 2;

        float bitmapScaleX = (float) glyphWidth / (float) characterWidth;
        float bitmapScaleY = (float) glyphHeight / (float) characterHeight;

        int[] bitmap = new int[bitmapWidth * bitmapHeight];
        for (int y = -padding; y < characterHeight + padding; y++) {
            for (int x = -padding; x < characterWidth + padding; x++) {
                int pixelX = (int) mapRange(x, -padding, characterWidth + padding,
                        -padding * bitmapScaleX, (characterWidth + padding) * bitmapScaleX);
                int pixelY = (int) mapRange(y, -padding, characterHeight + padding,
                        -padding * bitmapScaleY, (characterHeight + padding) * bitmapScaleY);
                float val = findNearestPixel(pixelX, pixelY, glyphBitmap,
                        glyphWidth, glyphHeight, spread);
                bitmap[(x + padding) + ((y + padding) * bitmapWidth)] = (int) (val * 255.0f);
            }
        }

        BufferedImage glyphImage = new BufferedImage(bitmapWidth, bitmapHeight, BufferedImage.TYPE_INT_ARGB);
        int x = 0;
        int y = 0;
        for (int byteAsInt : bitmap) {
            int argb = (byteAsInt << 24) | (byteAsInt << 16) | (byteAsInt << 8) | byteAsInt;
            glyphImage.setRGB(x, y, argb);
            x++;
            if (x >= bitmapWidth) {
                x = 0;
                y++;
            }
            if (y >= bitmapHeight) {
                break;
            }
        }

        Advance advance = glyphSlot.getAdvance();
        float xAdvance = (advance.getX() / fontScale) / 64;
        return new Glyph(
                glyphImage,
                glyphImage.getWidth(),
                glyphImage.getHeight(),
                xAdvance,
                glyphSlot.getBitmapLeft(),
                glyphSlot.getBitmapTop()
        );
    }

    float mapRange(float val, float in_min, float in_max, float out_min, float out_max) {
        return (val - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    int getPixel(int x, int y, byte[] bitmap, int width, int height) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return (bitmap[x + y * width] & 0xFF) == 0 ? 0 : 1;
        }
        return 0;
    }

    float findNearestPixel(int pixelX, int pixelY, byte[] bitmap, int width, int height, int spread) {
        int state = getPixel(pixelX, pixelY, bitmap, width, height);
        int minX = pixelX - spread;
        int maxX = pixelX + spread;
        int minY = pixelY - spread;
        int maxY = pixelY + spread;

        float minDistance = spread * spread;
        for (int y = minY; y < maxY; y++) {
            for (int x = minX; x < maxX; x++) {
                int pixelState = getPixel(x, y, bitmap, width, height);
                float dxSquared = (x - pixelX) * (x - pixelX);
                float dySquared = (y - pixelY) * (y - pixelY);
                float distanceSquared = dxSquared + dySquared;
                if (pixelState != state) {
                    minDistance = Math.min(distanceSquared, minDistance);
                }
            }
        }

        minDistance = (float) Math.sqrt(minDistance);
        float output = (minDistance - 0.5f) / (spread - 0.5f);
        output *= state == 0 ? -1 : 1;

        return (output + 1) * 0.5f;
    }

    public static final class Builder {

        private int padding = 15;
        private int upScaleResolution = 105;
        private int spread = upScaleResolution / 2;

        public Builder padding(int padding) {
            this.padding = padding;
            return this;
        }

        public Builder upScale(int upScaleResolution) {
            this.upScaleResolution = upScaleResolution;
            return this;
        }

        public Builder spread(int spread) {
            this.spread = spread;
            return this;
        }

        public GlyphGenerator build(InputStream inputStream, int fontSize) {
            if (inputStream == null) {
                throw new NullPointerException("Invalid font");
            }
            return new GlyphGenerator(
                    padding,
                    upScaleResolution,
                    spread
            ).load(inputStream, fontSize);
        }

        public GlyphGenerator build(File file, int fontSize) {
            if (!file.exists()) {
                throw new NullPointerException("Invalid font");
            }
            return new GlyphGenerator(
                    padding,
                    upScaleResolution,
                    spread
            ).load(file, fontSize);
        }

    }

}
