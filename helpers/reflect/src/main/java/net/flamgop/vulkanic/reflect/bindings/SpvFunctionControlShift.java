package net.flamgop.vulkanic.reflect.bindings;

public enum SpvFunctionControlShift {
    SpvFunctionControlInlineShift(0),
    SpvFunctionControlDontInlineShift(1),
    SpvFunctionControlPureShift(2),
    SpvFunctionControlConstShift(3),
    SpvFunctionControlOptNoneEXTShift(16),
    SpvFunctionControlOptNoneINTELShift(16),
    SpvFunctionControlMax(2147483647);

    private final int value;

    SpvFunctionControlShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvFunctionControlShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvFunctionControlShift value: " + value);
    }
}
