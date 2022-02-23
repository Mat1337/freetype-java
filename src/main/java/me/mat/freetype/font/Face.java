package me.mat.freetype.font;

import lombok.Getter;
import lombok.NonNull;
import me.mat.freetype.font.glyph.GlyphSlot;
import me.mat.freetype.font.kerning.Kerning;
import me.mat.freetype.font.kerning.KerningMode;
import me.mat.freetype.font.size.Size;
import me.mat.freetype.font.size.SizeRequest;
import me.mat.freetype.util.MemoryClass;

import java.nio.ByteBuffer;

@Getter
public class Face extends MemoryClass {

    private ByteBuffer buffer;
    private int faceIndex;

    public Face(@NonNull long pointer) {
        super(pointer);
    }

    public Face(@NonNull long pointer, ByteBuffer buffer, int faceIndex) {
        super(pointer);
        this.buffer = buffer;
        this.faceIndex = faceIndex;
    }

    protected native boolean deleteFace();

    public boolean delete() {
        if (buffer != null) {
            deleteBuffer(buffer);
        }
        return deleteFace();
    }

    public native int getAscender();

    public native int getDescender();

    public native long getFaceFlags();

    public native int getFaceIndex();

    public native String getFamilyName();

    public native int getHeight();

    public native int getMaxAdvanceHeight();

    public native int getMaxAdvanceWidth();

    public native int getNumFaces();

    public native int getNumGlyphs();

    public native long getStyleFlags();

    public native String getStyleName();

    public native int getUnderlinePosition();

    public native int getUnderlineThickness();

    public native int getUnitsPerEM();

    public native int getCharIndex(int code);

    public native boolean hasKerning();

    public native boolean selectSize(int strikeIndex);

    public native boolean setCharSize(int charWidth, int charHeight, int hResolution, int vResolution);

    public native boolean loadGlyph(int glyphIndex, int flags);

    public native boolean loadChar(char aChar, int flags);

    public native Kerning getKerning(char left, char right, int mode);

    public Kerning getKerning(char left, char right) {
        return getKerning(left, right, KerningMode.DEFAULT);
    }

    public Kerning getKerning(char left, char right, KerningMode mode) {
        return getKerning(left, right, mode.ordinal());
    }

    public native boolean setPixelSizes(float width, float height);

    protected native long getGlyphSlotPointer();

    public GlyphSlot getGlyphSlot() {
        long glyph = getGlyphSlotPointer();
        if (glyph <= 0)
            return null;
        return new GlyphSlot(glyph);
    }

    protected native long getSizePointer();

    public Size getSize() {
        long size = getSizePointer();
        if (size <= 0)
            return null;
        return new Size(size);
    }

    public native boolean checkTrueTypePatents();

    public native boolean setUnpatentedHinting(boolean newValue);

    public native boolean referenceFace();

    public native boolean requestSize(SizeRequest sr);

    public native int[] getFirstChar();

    public int getFirstCharAsCharCode() {
        return getFirstChar()[0];
    }

    public int getFirstCharAsGlyphIndex() {
        return getFirstChar()[1];
    }

    public native int getNextChar(long charcode);

    public native int getGlyphIndexByName(String name);

    public native long getTrackKerning(int point_size, int degree);

    public native String getGlyphName(int glyphIndex);

    public native String getPostscriptName();

    public native boolean selectCharmap(int encoding);

    public native boolean setCharmap(CharMap charmap);

    public native short getFSTypeFlags();

}
