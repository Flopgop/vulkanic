package net.flamgop.vulkanic.reflect.bindings;

public enum SpvCooperativeMatrixUse {
    SpvCooperativeMatrixUseMatrixAKHR(0),
    SpvCooperativeMatrixUseMatrixBKHR(1),
    SpvCooperativeMatrixUseMatrixAccumulatorKHR(2),
    SpvCooperativeMatrixUseMax(2147483647);

    private final int value;

    SpvCooperativeMatrixUse(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvCooperativeMatrixUse fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvCooperativeMatrixUse value: " + value);
    }
}
