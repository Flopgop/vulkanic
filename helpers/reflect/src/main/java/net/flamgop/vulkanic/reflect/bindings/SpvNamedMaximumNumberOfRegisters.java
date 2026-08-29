package net.flamgop.vulkanic.reflect.bindings;

public enum SpvNamedMaximumNumberOfRegisters {
    SpvNamedMaximumNumberOfRegistersAutoINTEL(0),
    SpvNamedMaximumNumberOfRegistersMax(2147483647);

    private final int value;

    SpvNamedMaximumNumberOfRegisters(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvNamedMaximumNumberOfRegisters fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvNamedMaximumNumberOfRegisters value: " + value);
    }
}
