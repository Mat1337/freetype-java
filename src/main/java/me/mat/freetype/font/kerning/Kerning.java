package me.mat.freetype.font.kerning;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Kerning {

    @NonNull
    private final int x;

    @NonNull
    private final int y;

}
