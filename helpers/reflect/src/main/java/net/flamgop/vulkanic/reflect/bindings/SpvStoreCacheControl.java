package net.flamgop.vulkanic.reflect.bindings;

public enum SpvStoreCacheControl {
    SpvStoreCacheControlUncachedINTEL(0),
    SpvStoreCacheControlWriteThroughINTEL(1),
    SpvStoreCacheControlWriteBackINTEL(2),
    SpvStoreCacheControlStreamingINTEL(3),
    SpvStoreCacheControlMax(2147483647);

    private final int value;

    SpvStoreCacheControl(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvStoreCacheControl fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvStoreCacheControl value: " + value);
    }
}
