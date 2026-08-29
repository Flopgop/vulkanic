package net.flamgop.vulkanic.reflect.bindings;

public enum SpvCooperativeMatrixLayout {
    SpvCooperativeMatrixLayoutRowMajorKHR(0),
    SpvCooperativeMatrixLayoutColumnMajorKHR(1),
    SpvCooperativeMatrixLayoutRowBlockedInterleavedARM(4202),
    SpvCooperativeMatrixLayoutColumnBlockedInterleavedARM(4203),
    SpvCooperativeMatrixLayoutMax(2147483647);

    private final int value;

    SpvCooperativeMatrixLayout(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvCooperativeMatrixLayout fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvCooperativeMatrixLayout value: " + value);
    }
}
