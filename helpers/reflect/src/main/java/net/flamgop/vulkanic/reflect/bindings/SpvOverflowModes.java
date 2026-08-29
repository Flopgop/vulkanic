package net.flamgop.vulkanic.reflect.bindings;

public enum SpvOverflowModes {
    SpvOverflowModesWRAP(0),
    SpvOverflowModesSAT(1),
    SpvOverflowModesSAT_ZERO(2),
    SpvOverflowModesSAT_SYM(3),
    SpvOverflowModesMax(2147483647);

    private final int value;

    SpvOverflowModes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvOverflowModes fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvOverflowModes value: " + value);
    }
}
