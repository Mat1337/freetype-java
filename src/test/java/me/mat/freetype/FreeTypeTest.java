package me.mat.freetype;

import me.mat.freetype.font.glyph.Glyph;
import me.mat.freetype.font.glyph.GlyphGenerator;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FreeTypeTest {

    private GlyphGenerator.Builder builder = new GlyphGenerator.Builder()
            .padding(15)
            .upScale(105)
            .spread(52);

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

        GlyphGenerator glyphGenerator = builder.build(fontFile, 64);
        glyphGenerator.generateGlyphs(' ', '~');
        String text = "freetype-java";
        for (char aChar : text.toCharArray()) {
            Glyph glyph = glyphGenerator.getGlyph(aChar);
            if (glyph == null) {
                throw new IllegalArgumentException("Invalid glyph for '" + aChar + "'");
            }
            try {
                File charFile = new File(testsDirectory, aChar + ".png");
                ImageIO.write(
                        glyph.getImage(),
                        "png",
                        charFile
                );
                assert (charFile.exists());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
