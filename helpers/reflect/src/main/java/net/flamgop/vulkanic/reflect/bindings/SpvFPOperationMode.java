package net.flamgop.vulkanic.reflect.bindings;

public enum SpvFPOperationMode {
    SpvFPOperationModeIEEE(0),
    SpvFPOperationModeALT(1),
    SpvFPOperationModeMax(2147483647);

    private final int value;

    SpvFPOperationMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvFPOperationMode fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvFPOperationMode value: " + value);
    }
}
