package net.flamgop.vulkanic.reflect.bindings;

public enum SpvSamplerFilterMode {
    SpvSamplerFilterModeNearest(0),
    SpvSamplerFilterModeLinear(1),
    SpvSamplerFilterModeMax(2147483647);

    private final int value;

    SpvSamplerFilterMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvSamplerFilterMode fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvSamplerFilterMode value: " + value);
    }
}
