package me.mat.freetype.font.size;

import lombok.NonNull;
import me.mat.freetype.util.MemoryClass;

public class Size extends MemoryClass {

    public Size(@NonNull long pointer) {
        super(pointer);
    }

    protected native long getSizeMetricsPointer();

    public SizeMetrics getMetrics() {
        long sizeMetrics = getSizeMetricsPointer();
        if (sizeMetrics <= 0)
            return null;
        return new SizeMetrics(sizeMetrics);
    }

}
