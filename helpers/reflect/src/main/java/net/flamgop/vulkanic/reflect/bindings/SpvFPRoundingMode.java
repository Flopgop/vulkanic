package net.flamgop.vulkanic.reflect.bindings;

public enum SpvFPRoundingMode {
    SpvFPRoundingModeRTE(0),
    SpvFPRoundingModeRTZ(1),
    SpvFPRoundingModeRTP(2),
    SpvFPRoundingModeRTN(3),
    SpvFPRoundingModeMax(2147483647);

    private final int value;

    SpvFPRoundingMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvFPRoundingMode fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvFPRoundingMode value: " + value);
    }
}
