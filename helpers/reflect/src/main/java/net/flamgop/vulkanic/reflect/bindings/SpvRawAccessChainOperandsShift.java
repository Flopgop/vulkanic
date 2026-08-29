package net.flamgop.vulkanic.reflect.bindings;

public enum SpvRawAccessChainOperandsShift {
    SpvRawAccessChainOperandsRobustnessPerComponentNVShift(0),
    SpvRawAccessChainOperandsRobustnessPerElementNVShift(1),
    SpvRawAccessChainOperandsMax(2147483647);

    private final int value;

    SpvRawAccessChainOperandsShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvRawAccessChainOperandsShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvRawAccessChainOperandsShift value: " + value);
    }
}
