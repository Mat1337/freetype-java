package me.mat.freetype.font.glyph;

import lombok.Setter;
import me.mat.freetype.FreeType;
import me.mat.freetype.font.Face;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GlyphGenerator {

    private final Map<Character, Glyph> glyphMap;
    private final Face face;
    private final int fontSize;

    @Setter
    private boolean generateMissing = false;

    @Setter
    private int padding = 15;

    @Setter
    private int upscaleResolution = 105;

    @Setter
    private int spread = upscaleResolution / 2;

    public GlyphGenerator(File file, int fontSize) {
        if (!file.exists()) {
            throw new NullPointerException("Invalid font");
        }
        try {
            FreeType.initialize();
            this.glyphMap = new HashMap<>();
            this.face = FreeType.newFace(file);
            this.face.setPixelSizes(0, fontSize);
            this.fontSize = fontSize;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GlyphGenerator(InputStream inputStream, int fontSize) {
        if (inputStream == null) {
            throw new NullPointerException("Invalid font");
        }
        try {
            FreeType.initialize();
            this.glyphMap = new HashMap<>();
            this.face = FreeType.newFace(inputStream);
            this.face.setPixelSizes(0, fontSize);
            this.fontSize = fontSize;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Glyph getGlyph(char aChar) {
        if (!glyphMap.containsKey(aChar)) {
            if (!generateMissing) {
                throw new IllegalArgumentException("Invalid glyph for '" + aChar + "'");
            }
            return generateGlyph(aChar);
        }
        return glyphMap.get(aChar);
    }

    public GlyphGenerator generateGlyphs(char start, char end) {
        for (int i = start; i < end; i++) {
            generateGlyph((char) i);
        }
        return this;
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
        face.setPixelSizes(0, upscaleResolution);
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

        float widthScale = (float) glyphWidth / (float) upscaleResolution;
        float heightScale = (float) glyphHeight / (float) upscaleResolution;

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
            int argb = (255 << 24) | (byteAsInt << 16) | (byteAsInt << 8) | byteAsInt;
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

        face.setPixelSizes(0, fontSize);
        if (face.loadChar(aChar, FreeType.LOAD_RENDER)) {
            System.out.println("FreeType could not generate character.");
            return null;
        }

        return new Glyph(
                glyphImage,
                glyphImage.getWidth(),
                glyphImage.getHeight(),
                face.getGlyphSlot().getBitmap().getWidth(),
                0
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


}
