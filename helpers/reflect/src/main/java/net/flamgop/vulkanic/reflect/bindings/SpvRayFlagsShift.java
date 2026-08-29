package net.flamgop.vulkanic.reflect.bindings;

public enum SpvRayFlagsShift {
    SpvRayFlagsOpaqueKHRShift(0),
    SpvRayFlagsNoOpaqueKHRShift(1),
    SpvRayFlagsTerminateOnFirstHitKHRShift(2),
    SpvRayFlagsSkipClosestHitShaderKHRShift(3),
    SpvRayFlagsCullBackFacingTrianglesKHRShift(4),
    SpvRayFlagsCullFrontFacingTrianglesKHRShift(5),
    SpvRayFlagsCullOpaqueKHRShift(6),
    SpvRayFlagsCullNoOpaqueKHRShift(7),
    SpvRayFlagsSkipBuiltinPrimitivesNVShift(8),
    SpvRayFlagsSkipTrianglesKHRShift(8),
    SpvRayFlagsSkipAABBsKHRShift(9),
    SpvRayFlagsForceOpacityMicromap2StateEXTShift(10),
    SpvRayFlagsForceOpacityMicromap2StateKHRShift(10),
    SpvRayFlagsMax(2147483647);

    private final int value;

    SpvRayFlagsShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvRayFlagsShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvRayFlagsShift value: " + value);
    }
}
