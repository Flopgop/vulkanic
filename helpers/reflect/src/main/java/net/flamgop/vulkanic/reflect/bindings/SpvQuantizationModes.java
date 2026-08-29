package net.flamgop.vulkanic.reflect.bindings;

public enum SpvQuantizationModes {
    SpvQuantizationModesTRN(0),
    SpvQuantizationModesTRN_ZERO(1),
    SpvQuantizationModesRND(2),
    SpvQuantizationModesRND_ZERO(3),
    SpvQuantizationModesRND_INF(4),
    SpvQuantizationModesRND_MIN_INF(5),
    SpvQuantizationModesRND_CONV(6),
    SpvQuantizationModesRND_CONV_ODD(7),
    SpvQuantizationModesMax(2147483647);

    private final int value;

    SpvQuantizationModes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvQuantizationModes fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvQuantizationModes value: " + value);
    }
}
