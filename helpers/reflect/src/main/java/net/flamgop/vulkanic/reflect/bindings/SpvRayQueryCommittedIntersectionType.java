package net.flamgop.vulkanic.reflect.bindings;

public enum SpvRayQueryCommittedIntersectionType {
    SpvRayQueryCommittedIntersectionTypeRayQueryCommittedIntersectionNoneKHR(0),
    SpvRayQueryCommittedIntersectionTypeRayQueryCommittedIntersectionTriangleKHR(1),
    SpvRayQueryCommittedIntersectionTypeRayQueryCommittedIntersectionGeneratedKHR(2),
    SpvRayQueryCommittedIntersectionTypeMax(2147483647);

    private final int value;

    SpvRayQueryCommittedIntersectionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvRayQueryCommittedIntersectionType fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvRayQueryCommittedIntersectionType value: " + value);
    }
}
