package net.flamgop.vulkanic.reflect.bindings;

public enum SpvCooperativeMatrixReduceShift {
    SpvCooperativeMatrixReduceRowShift(0),
    SpvCooperativeMatrixReduceColumnShift(1),
    SpvCooperativeMatrixReduce2x2Shift(2),
    SpvCooperativeMatrixReduceMax(2147483647);

    private final int value;

    SpvCooperativeMatrixReduceShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvCooperativeMatrixReduceShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvCooperativeMatrixReduceShift value: " + value);
    }
}
