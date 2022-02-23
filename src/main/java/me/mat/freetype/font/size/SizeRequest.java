package me.mat.freetype.font.size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class SizeRequest {

    @NonNull
    private final int type;

    @NonNull
    private final long width;

    @NonNull
    private final long height;

    @NonNull
    private final int horizontalResolution;

    @NonNull
    private final int verticalResolution;

    public SizeRequestType getType() {
        return SizeRequestType.values()[type];
    }

}
