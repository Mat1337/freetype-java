package me.mat.freetype;

import me.mat.freetype.font.Face;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FreeTypeTest {

    public static void main(String[] args) {
        FreeType.initialize();

        Face face = FreeType.newFace("C:/Windows/Fonts/arial.ttf");
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
        generateBitmap('b', face, 64);
        generateBitmap('c', face, 32);

        face.delete();
        FreeType.free();
    }

    private static void generateBitmap(char aChar, Face face, int fontSize) {
        face.setPixelSizes(0, fontSize);
        if (face.loadChar(aChar, FreeType.LOAD_RENDER)) {
            System.err.println("[FREETYPE]> Could not generate character '" + aChar + "'");
        }
        int glyphWidth = face.getGlyphSlot().getBitmap().getWidth();
        int glyphHeight = face.getGlyphSlot().getBitmap().getRows();

        int x = 0, y = 0;
        byte[] glyphBitmap = new byte[glyphWidth * glyphHeight];
        face.getGlyphSlot().getBitmap().getBuffer().get(glyphBitmap, 0, glyphWidth * glyphHeight);
        BufferedImage image = new BufferedImage(glyphWidth, glyphHeight, BufferedImage.TYPE_INT_ARGB);
        for (byte pixel : glyphBitmap) {
            int b = pixel & 0xFF;
            int argb = (255 << 24) | (b << 16) | (b << 8) | b;
            image.setRGB(x, y, argb);
            x++;
            if (x >= glyphWidth) {
                x = 0;
                y++;
            }
            if (y >= glyphHeight) {
                break;
            }
        }
        File file = new File(aChar + ".png");
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
