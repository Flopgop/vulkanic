package net.flamgop.vulkanic.reflect.bindings;

public enum SpvScope {
    SpvScopeCrossDevice(0),
    SpvScopeDevice(1),
    SpvScopeWorkgroup(2),
    SpvScopeSubgroup(3),
    SpvScopeInvocation(4),
    SpvScopeQueueFamily(5),
    SpvScopeQueueFamilyKHR(5),
    SpvScopeShaderCallKHR(6),
    SpvScopeMax(2147483647);

    private final int value;

    SpvScope(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvScope fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvScope value: " + value);
    }
}
