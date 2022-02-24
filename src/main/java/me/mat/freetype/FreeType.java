package me.mat.freetype;

import me.mat.freetype.font.Face;
import me.mat.freetype.util.OperatingSystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FreeType {

    /*
        Load parameters
     */

    public static final int LOAD_DEFAULT = 0x0;
    public static final int LOAD_NO_SCALE = 1;
    public static final int LOAD_NO_HINTING = (1 << 1);
    public static final int LOAD_RENDER = (1 << 2);
    public static final int LOAD_NO_BITMAP = (1 << 3);
    public static final int LOAD_VERTICAL_LAYOUT = (1 << 4);
    public static final int LOAD_FORCE_AUTOHINT = (1 << 5);
    public static final int LOAD_CROP_BITMAP = (1 << 6);
    public static final int LOAD_PEDANTIC = (1 << 7);
    public static final int LOAD_IGNORE_GLOBAL_ADVANCE_WIDTH = (1 << 9);
    public static final int LOAD_NO_RECURSE = (1 << 10);
    public static final int LOAD_IGNORE_TRANSFORM = (1 << 11);
    public static final int LOAD_MONOCHROME = (1 << 12);
    public static final int LOAD_LINEAR_DESIGN = (1 << 13);
    public static final int LOAD_NO_AUTOHINT = (1 << 15);
    public static final int LOAD_COLOR = (1 << 20);
    public static final int LOAD_COMPUTE_METRICS = (1 << 21);

    /*
        Types
     */

    public static final int TYPE_INSTALLABLE_EMBEDDING = 0x0000;
    public static final int TYPE_RESTRICTED_LICENSE_EMBEDDING = 0x0002;
    public static final int TYPE_PREVIEW_AND_PRINT_EMBEDDING = 0x0004;
    public static final int TYPE_EDITABLE_EMBEDDING = 0x0008;
    public static final int TYPE_NO_SUBSETTING = 0x0100;
    public static final int TYPE_BITMAP_EMBEDDING_ONLY = 0x0200;

    /*
        Encodings
     */

    public static final int ENCODING_NONE = 0x0;
    public static final int ENCODING_MS_SYMBOL = 1937337698;
    public static final int ENCODING_UNICODE = 1970170211;
    public static final int ENCODING_SJIS = 1936353651;
    public static final int ENCODING_GB2312 = 1734484000;
    public static final int ENCODING_BIG5 = 1651074869;
    public static final int ENCODING_WANSUNG = 2002873971;
    public static final int ENCODING_JOHAB = 1785686113;
    public static final int ENCODING_ADOBE_STANDARD = 1094995778;
    public static final int ENCODING_ADOBE_EXPERT = 1094992453;
    public static final int ENCODING_ADOBE_CUSTOM = 1094992451;
    public static final int ENCODING_ADOBE_LATIN_1 = 1818326065;
    public static final int ENCODING_OLD_LATIN_2 = 1818326066;
    public static final int ENCODING_APPLE_ROMAN = 1634889070;

    // location of the native libraries
    private static final File TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));
    private static final File FREE_TYPE_DLL = new File(TEMP_DIRECTORY, "freetype.dll");
    private static final File FREE_TYPE_NATIVE_DLL = new File(TEMP_DIRECTORY, "freetype-native.dll");

    // native handle
    private static long handle;
    private static FreeTypeHandle freeTypeHandle;

    // version
    private static int major;
    private static int minor;
    private static int patch;

    public static native void free();

    public static Face newFace(File file) {
        return freeTypeHandle.newFace(file.getAbsolutePath());
    }

    public static Face newFace(InputStream inputStream) {
        return freeTypeHandle.newFaceFromMemory(inputStream);
    }

    public static void initialize() {
        if (isProduction()) {
            loadLibrary(FREE_TYPE_DLL.getAbsolutePath());
            loadLibrary(FREE_TYPE_NATIVE_DLL.getAbsolutePath());
        } else {
            switch (OperatingSystem.getOperatingSystem()) {
                case WINDOWS:
                    loadLibrary("lib/natives/freetype.dll");
                    loadLibrary("lib/natives/freetype-native.dll");
                    break;
                case LINUX:
                    loadLibrary("lib/natives/libfreetype.so");
                    loadLibrary("lib/natives/libfreetype-native.so");
                    break;
                case MAC:
                case OTHER:
                    throw new RuntimeException("Unsupported Operating System");
            }
        }
        if (handle == 0) {
            throw new RuntimeException("FreeTypeNative has failed to load");
        }
        freeTypeHandle = new FreeTypeHandle(handle);

        System.out.println("|------------------------------|");
        System.out.println("|   FreeType Native Library    |");
        System.out.println("|------------------------------|");
        System.out.println("> Major: " + major);
        System.out.println("> Minor: " + minor);
        System.out.println("> Patch: " + patch);
        System.out.println("|------------------------------");
    }

    private static boolean isProduction() {
        InputStream freeType = FreeType.class.getResourceAsStream("/freetype.dll");
        InputStream freeTypeNative = FreeType.class.getResourceAsStream("/freetype-native.dll");
        if (freeType == null
                || freeTypeNative == null) {
            return false;
        }

        extractResource(FREE_TYPE_DLL.getAbsolutePath(), freeType);
        extractResource(FREE_TYPE_NATIVE_DLL.getAbsolutePath(), freeTypeNative);
        return true;
    }

    private static void extractResource(String outputPath, InputStream inputStream) {
        try {
            Files.copy(
                    inputStream,
                    Paths.get(outputPath),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadLibrary(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("'" + file.getAbsolutePath() + "' does not exist");
        }
        try {
            System.load(file.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
