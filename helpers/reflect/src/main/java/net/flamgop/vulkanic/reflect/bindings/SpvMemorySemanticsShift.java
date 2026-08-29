package net.flamgop.vulkanic.reflect.bindings;

public enum SpvMemorySemanticsShift {
    SpvMemorySemanticsAcquireShift(1),
    SpvMemorySemanticsReleaseShift(2),
    SpvMemorySemanticsAcquireReleaseShift(3),
    SpvMemorySemanticsSequentiallyConsistentShift(4),
    SpvMemorySemanticsUniformMemoryShift(6),
    SpvMemorySemanticsSubgroupMemoryShift(7),
    SpvMemorySemanticsWorkgroupMemoryShift(8),
    SpvMemorySemanticsCrossWorkgroupMemoryShift(9),
    SpvMemorySemanticsAtomicCounterMemoryShift(10),
    SpvMemorySemanticsImageMemoryShift(11),
    SpvMemorySemanticsOutputMemoryShift(12),
    SpvMemorySemanticsOutputMemoryKHRShift(12),
    SpvMemorySemanticsMakeAvailableShift(13),
    SpvMemorySemanticsMakeAvailableKHRShift(13),
    SpvMemorySemanticsMakeVisibleShift(14),
    SpvMemorySemanticsMakeVisibleKHRShift(14),
    SpvMemorySemanticsVolatileShift(15),
    SpvMemorySemanticsMax(2147483647);

    private final int value;

    SpvMemorySemanticsShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvMemorySemanticsShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvMemorySemanticsShift value: " + value);
    }
}
