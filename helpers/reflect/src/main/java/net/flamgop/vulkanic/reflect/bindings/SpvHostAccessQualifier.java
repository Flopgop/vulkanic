package net.flamgop.vulkanic.reflect.bindings;

public enum SpvHostAccessQualifier {
    SpvHostAccessQualifierNoneINTEL(0),
    SpvHostAccessQualifierReadINTEL(1),
    SpvHostAccessQualifierWriteINTEL(2),
    SpvHostAccessQualifierReadWriteINTEL(3),
    SpvHostAccessQualifierMax(2147483647);

    private final int value;

    SpvHostAccessQualifier(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvHostAccessQualifier fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvHostAccessQualifier value: " + value);
    }
}
