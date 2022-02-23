package me.mat.freetype.font;

import lombok.NonNull;
import me.mat.freetype.util.MemoryClass;

import java.nio.ByteBuffer;

public class Bitmap extends MemoryClass {

    public Bitmap(@NonNull long pointer) {
        super(pointer);
    }

    public native int getWidth();

    public native int getRows();

    public native int getPitch();

    public native short getNumGrays();

    public native char getPaletteMode();

    public native char getPixelMode();

    public native ByteBuffer getBuffer();

}
