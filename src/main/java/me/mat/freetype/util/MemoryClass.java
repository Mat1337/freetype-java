package me.mat.freetype.util;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.nio.ByteBuffer;

@AllArgsConstructor
public class MemoryClass {

    @NonNull
    private final long pointer;

    protected native ByteBuffer createBuffer(int size);

    protected native void fillBuffer(byte[] bytes, ByteBuffer buffer, int length);

    protected native void deleteBuffer(ByteBuffer buffer);

}
