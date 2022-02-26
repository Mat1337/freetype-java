package me.mat.freetype.font.face;

import lombok.NonNull;
import me.mat.freetype.util.MemoryClass;

public class BBox extends MemoryClass {

    public BBox(@NonNull long pointer) {
        super(pointer);
    }

    public native long xMin();

    public native long yMin();

    public native long xMax();

    public native long yMax();

}
