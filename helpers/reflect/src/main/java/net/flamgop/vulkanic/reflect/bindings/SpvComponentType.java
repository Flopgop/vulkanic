package net.flamgop.vulkanic.reflect.bindings;

public enum SpvComponentType {
    SpvComponentTypeFloat16NV(0),
    SpvComponentTypeFloat32NV(1),
    SpvComponentTypeFloat64NV(2),
    SpvComponentTypeSignedInt8NV(3),
    SpvComponentTypeSignedInt16NV(4),
    SpvComponentTypeSignedInt32NV(5),
    SpvComponentTypeSignedInt64NV(6),
    SpvComponentTypeUnsignedInt8NV(7),
    SpvComponentTypeUnsignedInt16NV(8),
    SpvComponentTypeUnsignedInt32NV(9),
    SpvComponentTypeUnsignedInt64NV(10),
    SpvComponentTypeSignedInt8PackedNV(1000491000),
    SpvComponentTypeUnsignedInt8PackedNV(1000491001),
    SpvComponentTypeFloatE4M3NV(1000491002),
    SpvComponentTypeFloatE5M2NV(1000491003),
    SpvComponentTypeMax(2147483647);

    private final int value;

    SpvComponentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvComponentType fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvComponentType value: " + value);
    }
}
