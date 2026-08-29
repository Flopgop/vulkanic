package net.flamgop.vulkanic.reflect.bindings;

public enum SpvRayQueryCandidateIntersectionType {
    SpvRayQueryCandidateIntersectionTypeRayQueryCandidateIntersectionTriangleKHR(0),
    SpvRayQueryCandidateIntersectionTypeRayQueryCandidateIntersectionAABBKHR(1),
    SpvRayQueryCandidateIntersectionTypeMax(2147483647);

    private final int value;

    SpvRayQueryCandidateIntersectionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvRayQueryCandidateIntersectionType fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvRayQueryCandidateIntersectionType value: " + value);
    }
}
