package me.mat.freetype.font.glyph;

import lombok.NonNull;
import me.mat.freetype.font.Bitmap;
import me.mat.freetype.util.MemoryClass;

public class GlyphSlot extends MemoryClass {

    public GlyphSlot(@NonNull long pointer) {
        super(pointer);
    }

    protected native long getBitmapPointer();

    public Bitmap getBitmap() {
        long bitmap = getBitmapPointer();
        if (bitmap <= 0)
            return null;
        return new Bitmap(bitmap);
    }

    protected native long getMetricsPointer();

    public GlyphMetrics getMetrics() {
        long metrics = getMetricsPointer();
        if (metrics <= 0)
            return null;
        return new GlyphMetrics(metrics);
    }

    public native long getLinearHorizontalAdvance();

    public native long getLinearVerticalAdvance();

    public native Advance getAdvance();

    public native int getFormat();

    public native int getBitmapLeft();

    public native int getBitmapTop();

}
