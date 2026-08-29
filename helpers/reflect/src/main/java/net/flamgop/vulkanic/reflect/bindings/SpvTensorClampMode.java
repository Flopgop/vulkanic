package net.flamgop.vulkanic.reflect.bindings;

public enum SpvTensorClampMode {
    SpvTensorClampModeUndefined(0),
    SpvTensorClampModeConstant(1),
    SpvTensorClampModeClampToEdge(2),
    SpvTensorClampModeRepeat(3),
    SpvTensorClampModeRepeatMirrored(4),
    SpvTensorClampModeMax(2147483647);

    private final int value;

    SpvTensorClampMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvTensorClampMode fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvTensorClampMode value: " + value);
    }
}
