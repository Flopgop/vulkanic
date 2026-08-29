package net.flamgop.vulkanic.reflect.bindings;

public enum SpvAccessQualifier {
    SpvAccessQualifierReadOnly(0),
    SpvAccessQualifierWriteOnly(1),
    SpvAccessQualifierReadWrite(2),
    SpvAccessQualifierMax(2147483647);

    private final int value;

    SpvAccessQualifier(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvAccessQualifier fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvAccessQualifier value: " + value);
    }
}
