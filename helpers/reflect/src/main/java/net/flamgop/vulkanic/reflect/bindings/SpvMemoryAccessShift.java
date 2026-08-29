package net.flamgop.vulkanic.reflect.bindings;

public enum SpvMemoryAccessShift {
    SpvMemoryAccessVolatileShift(0),
    SpvMemoryAccessAlignedShift(1),
    SpvMemoryAccessNontemporalShift(2),
    SpvMemoryAccessMakePointerAvailableShift(3),
    SpvMemoryAccessMakePointerAvailableKHRShift(3),
    SpvMemoryAccessMakePointerVisibleShift(4),
    SpvMemoryAccessMakePointerVisibleKHRShift(4),
    SpvMemoryAccessNonPrivatePointerShift(5),
    SpvMemoryAccessNonPrivatePointerKHRShift(5),
    SpvMemoryAccessAliasScopeINTELMaskShift(16),
    SpvMemoryAccessNoAliasINTELMaskShift(17),
    SpvMemoryAccessMax(2147483647);

    private final int value;

    SpvMemoryAccessShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvMemoryAccessShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvMemoryAccessShift value: " + value);
    }
}
