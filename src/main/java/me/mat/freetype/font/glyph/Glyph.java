package me.mat.freetype.font.glyph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Getter
@AllArgsConstructor
public class Glyph {

    @NonNull
    private final BufferedImage image;

    @NonNull
    private final int imageWidth;

    @NonNull
    private final int imageHeight;

    @NonNull
    private final float xAdvance;

    @NonNull
    private final float xOffset;

    @NonNull
    private final float yOffset;

}
