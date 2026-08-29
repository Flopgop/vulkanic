package net.flamgop.vulkanic.reflect.bindings;

public enum SpvFPDenormMode {
    SpvFPDenormModePreserve(0),
    SpvFPDenormModeFlushToZero(1),
    SpvFPDenormModeMax(2147483647);

    private final int value;

    SpvFPDenormMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvFPDenormMode fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvFPDenormMode value: " + value);
    }
}
