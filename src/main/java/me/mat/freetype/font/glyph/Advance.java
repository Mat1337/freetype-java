package me.mat.freetype.font.glyph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Advance {

    @NonNull
    private final int x;

    @NonNull
    private final int y;

}
