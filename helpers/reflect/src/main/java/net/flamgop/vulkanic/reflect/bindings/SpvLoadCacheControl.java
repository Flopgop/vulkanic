package net.flamgop.vulkanic.reflect.bindings;

public enum SpvLoadCacheControl {
    SpvLoadCacheControlUncachedINTEL(0),
    SpvLoadCacheControlCachedINTEL(1),
    SpvLoadCacheControlStreamingINTEL(2),
    SpvLoadCacheControlInvalidateAfterReadINTEL(3),
    SpvLoadCacheControlConstCachedINTEL(4),
    SpvLoadCacheControlMax(2147483647);

    private final int value;

    SpvLoadCacheControl(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvLoadCacheControl fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvLoadCacheControl value: " + value);
    }
}
