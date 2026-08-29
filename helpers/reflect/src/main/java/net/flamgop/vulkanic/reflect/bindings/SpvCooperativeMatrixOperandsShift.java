package net.flamgop.vulkanic.reflect.bindings;

public enum SpvCooperativeMatrixOperandsShift {
    SpvCooperativeMatrixOperandsMatrixASignedComponentsKHRShift(0),
    SpvCooperativeMatrixOperandsMatrixBSignedComponentsKHRShift(1),
    SpvCooperativeMatrixOperandsMatrixCSignedComponentsKHRShift(2),
    SpvCooperativeMatrixOperandsMatrixResultSignedComponentsKHRShift(3),
    SpvCooperativeMatrixOperandsSaturatingAccumulationKHRShift(4),
    SpvCooperativeMatrixOperandsMax(2147483647);

    private final int value;

    SpvCooperativeMatrixOperandsShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvCooperativeMatrixOperandsShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvCooperativeMatrixOperandsShift value: " + value);
    }
}
