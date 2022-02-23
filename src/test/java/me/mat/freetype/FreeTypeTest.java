package me.mat.freetype;

import me.mat.freetype.font.Face;

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

        face.delete();
        FreeType.free();
    }

}
