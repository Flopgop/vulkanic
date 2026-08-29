package net.flamgop.vulkanic.reflect.bindings;

public enum SpvLinkageType {
    SpvLinkageTypeExport(0),
    SpvLinkageTypeImport(1),
    SpvLinkageTypeLinkOnceODR(2),
    SpvLinkageTypeWeakAMD(3),
    SpvLinkageTypeMax(2147483647);

    private final int value;

    SpvLinkageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvLinkageType fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvLinkageType value: " + value);
    }
}
