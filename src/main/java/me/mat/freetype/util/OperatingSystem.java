package me.mat.freetype.util;

public enum OperatingSystem {

    MAC("mac", "darwin"),
    WINDOWS("win"),
    LINUX("nux"),
    OTHER("generic");

    private static OperatingSystem detectedOS;

    private final String[] keys;

    OperatingSystem(String... keys) {
        this.keys = keys;
    }

    private boolean match(String osKey) {
        for (String key : keys) {
            if (osKey.contains(key))
                return true;
        }
        return false;
    }

    public static OperatingSystem getOperatingSystem() {
        if (detectedOS == null)
            detectedOS = getOperatingSystemType(System.getProperty("os.name", OTHER.keys[0]).toLowerCase());
        return detectedOS;
    }

    private static OperatingSystem getOperatingSystemType(String osKey) {
        for (OperatingSystem operatingSystem : values()) {
            if (operatingSystem.match(osKey))
                return operatingSystem;
        }
        return OTHER;
    }
}