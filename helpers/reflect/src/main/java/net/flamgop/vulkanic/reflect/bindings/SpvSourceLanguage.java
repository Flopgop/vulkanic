package net.flamgop.vulkanic.reflect.bindings;

public enum SpvSourceLanguage {
    SpvSourceLanguageUnknown(0),
    SpvSourceLanguageESSL(1),
    SpvSourceLanguageGLSL(2),
    SpvSourceLanguageOpenCL_C(3),
    SpvSourceLanguageOpenCL_CPP(4),
    SpvSourceLanguageHLSL(5),
    SpvSourceLanguageCPP_for_OpenCL(6),
    SpvSourceLanguageSYCL(7),
    SpvSourceLanguageHERO_C(8),
    SpvSourceLanguageNZSL(9),
    SpvSourceLanguageWGSL(10),
    SpvSourceLanguageSlang(11),
    SpvSourceLanguageZig(12),
    SpvSourceLanguageRust(13),
    SpvSourceLanguagePred(14),
    SpvSourceLanguageApilaJai(15),
    SpvSourceLanguageMax(2147483647);

    private final int value;

    SpvSourceLanguage(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvSourceLanguage fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvSourceLanguage value: " + value);
    }
}
