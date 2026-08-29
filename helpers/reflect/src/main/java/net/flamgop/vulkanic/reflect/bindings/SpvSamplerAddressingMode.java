package net.flamgop.vulkanic.reflect.bindings;

public enum SpvSamplerAddressingMode {
    SpvSamplerAddressingModeNone(0),
    SpvSamplerAddressingModeClampToEdge(1),
    SpvSamplerAddressingModeClamp(2),
    SpvSamplerAddressingModeRepeat(3),
    SpvSamplerAddressingModeRepeatMirrored(4),
    SpvSamplerAddressingModeMax(2147483647);

    private final int value;

    SpvSamplerAddressingMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvSamplerAddressingMode fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvSamplerAddressingMode value: " + value);
    }
}
