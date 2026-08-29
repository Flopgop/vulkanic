package net.flamgop.vulkanic.reflect.bindings;

public enum SpvReflectArrayDimType {
    SPV_REFLECT_ARRAY_DIM_RUNTIME(0);

    private final int value;

    SpvReflectArrayDimType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvReflectArrayDimType fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvReflectArrayDimType value: " + value);
    }
}
