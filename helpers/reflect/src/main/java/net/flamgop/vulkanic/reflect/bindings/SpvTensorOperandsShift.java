package net.flamgop.vulkanic.reflect.bindings;

public enum SpvTensorOperandsShift {
    SpvTensorOperandsNontemporalARMShift(0),
    SpvTensorOperandsOutOfBoundsValueARMShift(1),
    SpvTensorOperandsMakeElementAvailableARMShift(2),
    SpvTensorOperandsMakeElementVisibleARMShift(3),
    SpvTensorOperandsNonPrivateElementARMShift(4),
    SpvTensorOperandsMax(2147483647);

    private final int value;

    SpvTensorOperandsShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvTensorOperandsShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvTensorOperandsShift value: " + value);
    }
}
