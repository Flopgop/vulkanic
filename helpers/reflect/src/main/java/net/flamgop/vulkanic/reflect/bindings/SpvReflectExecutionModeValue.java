package net.flamgop.vulkanic.reflect.bindings;

public enum SpvReflectExecutionModeValue {
    SPV_REFLECT_EXECUTION_MODE_SPEC_CONSTANT(0xFFFFFFFF);

    private final int value;

    SpvReflectExecutionModeValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvReflectExecutionModeValue fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvReflectExecutionModeValue value: " + value);
    }
}
