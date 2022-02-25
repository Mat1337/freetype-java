package me.mat.freetype;

import me.mat.freetype.font.Face;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;

public class FreeTypeTest {

    @Test
    public void freeType() {
        FreeType.initialize();
        Face face = FreeType.newFace(new File("lib/RobotoSerif-Regular.ttf"));
        System.out.println("--------------------------------------------");
        System.out.println("                Arial Font                  ");
        System.out.println("--------------------------------------------");
        System.out.println("Ascender: " + face.getAscender());
        System.out.println("Descender: " + face.getDescender());
        System.out.println("FaceFlags: " + face.getFaceFlags());
        System.out.println("FaceIndex: " + face.getFaceIndex());
        System.out.println("FamilyName: " + face.getFamilyName());
        System.out.println("Height: " + face.getHeight());
        System.out.println("MaxAdvanceHeight: " + face.getMaxAdvanceHeight());
        System.out.println("MaxAdvanceWidth: " + face.getMaxAdvanceWidth());
        System.out.println("NumFaces: " + face.getNumFaces());
        System.out.println("NumGlyphs: " + face.getNumGlyphs());
        System.out.println("StyleFlags: " + face.getStyleFlags());
        System.out.println("StyleName: " + face.getStyleName());
        System.out.println("UnderlinePosition: " + face.getUnderlinePosition());
        System.out.println("UnderlineThickness: " + face.getUnderlineThickness());
        System.out.println("UnitsPerEM: " + face.getUnitsPerEM());
        System.out.println("CharIndex('A'): " + face.getCharIndex('A'));
        System.out.println("HasKerning: " + face.hasKerning());
        System.out.println("--------------------------------------------");

        generateBitmap('A', face, 128);
        generateBitmap('b', face, 128);
        generateBitmap('c', face, 128);

        face.delete();
        FreeType.free();
    }

    private static void generateBitmap(char aChar, Face face, int fontSize) {
        int padding = 15;
        int upscaleResolution = 1024 / 10;
        int spread = upscaleResolution / 2;

        face.setPixelSizes(0, upscaleResolution);
        if (face.loadChar(aChar, FreeType.LOAD_RENDER)) {
            System.out.println("FreeType could not generate character.");
            return;
        }

        int glyphWidth = face.getGlyphSlot().getBitmap().getWidth();
        int glyphHeight = face.getGlyphSlot().getBitmap().getRows();
        byte[] glyphBitmap = new byte[glyphHeight * glyphWidth];
        face.getGlyphSlot().getBitmap().getBuffer()
                .get(glyphBitmap, 0, glyphWidth * glyphHeight);

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

        BufferedImage testImage = new BufferedImage(bitmapWidth, bitmapHeight, BufferedImage.TYPE_INT_ARGB);
        int x = 0;
        int y = 0;
        for (int byteAsInt : bitmap) {
            int argb = (255 << 24) | (byteAsInt << 16) | (byteAsInt << 8) | byteAsInt;
            testImage.setRGB(x, y, argb);
            x++;
            if (x >= bitmapWidth) {
                x = 0;
                y++;
            }
            if (y >= bitmapHeight) {
                break;
            }
        }
/*
            try {
                File output = new File(aChar + ".png");
                ImageIO.write(testImage, "png", output);
            } catch (IOException e) {
                e.printStackTrace();
            }
  */
    }

    private static float mapRange(float val, float in_min, float in_max,
                                  float out_min, float out_max) {
        return (val - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    private static int getPixel(int x, int y, byte[] bitmap, int width, int height) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return (bitmap[x + y * width] & 0xFF) == 0 ? 0 : 1;
        }
        return 0;
    }

    private static float findNearestPixel(int pixelX, int pixelY, byte[] bitmap,
                                          int width, int height, int spread) {
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
