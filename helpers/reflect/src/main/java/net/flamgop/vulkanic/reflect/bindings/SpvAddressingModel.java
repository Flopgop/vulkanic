package net.flamgop.vulkanic.reflect.bindings;

public enum SpvAddressingModel {
    SpvAddressingModelLogical(0),
    SpvAddressingModelPhysical32(1),
    SpvAddressingModelPhysical64(2),
    SpvAddressingModelPhysicalStorageBuffer64(5348),
    SpvAddressingModelPhysicalStorageBuffer64EXT(5348),
    SpvAddressingModelMax(2147483647);

    private final int value;

    SpvAddressingModel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvAddressingModel fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvAddressingModel value: " + value);
    }
}
