package net.flamgop.vulkanic.reflect.bindings;

public enum SpvTensorAddressingOperandsShift {
    SpvTensorAddressingOperandsTensorViewShift(0),
    SpvTensorAddressingOperandsDecodeFuncShift(1),
    SpvTensorAddressingOperandsDecodeVectorFuncShift(2),
    SpvTensorAddressingOperandsMax(2147483647);

    private final int value;

    SpvTensorAddressingOperandsShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvTensorAddressingOperandsShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvTensorAddressingOperandsShift value: " + value);
    }
}
