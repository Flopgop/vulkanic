package net.flamgop.vulkanic.reflect.bindings;

public enum SpvRayQueryIntersection {
    SpvRayQueryIntersectionRayQueryCandidateIntersectionKHR(0),
    SpvRayQueryIntersectionRayQueryCommittedIntersectionKHR(1),
    SpvRayQueryIntersectionMax(2147483647);

    private final int value;

    SpvRayQueryIntersection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvRayQueryIntersection fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvRayQueryIntersection value: " + value);
    }
}
