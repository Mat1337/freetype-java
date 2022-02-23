package me.mat.freetype.font.size;

import lombok.NonNull;
import me.mat.freetype.util.MemoryClass;

public class SizeMetrics extends MemoryClass {

    public SizeMetrics(@NonNull long pointer) {
        super(pointer);
    }

    public native int getAscender();

    public native int getDescender();

    public native int getHeight();

    public native int getMaxAdvance();

    public native int getXppem();

    public native int getYppem();

    public native int getXScale();

    public native int getYScale();

}
