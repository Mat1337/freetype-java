package me.mat.freetype;

import me.mat.freetype.font.glyph.Glyph;
import me.mat.freetype.font.glyph.GlyphGenerator;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FreeTypeTest {

    @Test
    public void freeType() {
        File fontFile = new File("lib/RobotoSerif-Regular.ttf");
        if (!fontFile.exists()) {
            throw new NullPointerException(fontFile.getAbsolutePath() + " does not exist");
        }
        File testsDirectory = new File("target/tests");
        if (!testsDirectory.exists()) {
            if (!testsDirectory.mkdirs()) {
                throw new RuntimeException("Failed to create the tests directory");
            }
        }

        GlyphGenerator glyphGenerator = new GlyphGenerator(
                fontFile,
                30
        ).generateGlyphs(' ', '~');

        String text = "freetype-java";
        for (char aChar : text.toCharArray()) {
            Glyph glyph = glyphGenerator.getGlyph(aChar);
            if (glyph == null) {
                throw new IllegalArgumentException("Invalid glyph for '" + aChar + "'");
            }
            try {
                ImageIO.write(
                        glyph.getImage(),
                        "png",
                        new File(testsDirectory, aChar + ".png")
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
