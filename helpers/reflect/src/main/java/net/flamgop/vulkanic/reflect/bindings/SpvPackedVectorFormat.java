package net.flamgop.vulkanic.reflect.bindings;

public enum SpvPackedVectorFormat {
    SpvPackedVectorFormatPackedVectorFormat4x8Bit(0),
    SpvPackedVectorFormatPackedVectorFormat4x8BitKHR(0),
    SpvPackedVectorFormatMax(2147483647);

    private final int value;

    SpvPackedVectorFormat(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvPackedVectorFormat fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvPackedVectorFormat value: " + value);
    }
}
