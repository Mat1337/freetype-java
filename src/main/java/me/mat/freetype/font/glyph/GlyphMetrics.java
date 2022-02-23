package me.mat.freetype.font.glyph;

import lombok.NonNull;
import me.mat.freetype.util.MemoryClass;

public class GlyphMetrics extends MemoryClass {

    public GlyphMetrics(@NonNull long pointer) {
        super(pointer);
    }

    public native int getWidth();

    public native int getHeight();

    public native int getHorizontalAdvance();

    public native int getVerticalAdvance();

    public native int getHorizontalBearingX();

    public native int getHorizontalBearingY();

    public native int getVerticalBearingX();

    public native int getVerticalBearingY();

}
