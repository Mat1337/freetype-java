package me.mat.freetype.font;

import lombok.NonNull;
import me.mat.freetype.util.MemoryClass;

public class CharMap extends MemoryClass {

    public CharMap(@NonNull long pointer) {
        super(pointer);
    }

    public native int getCharMapIndex();

}
