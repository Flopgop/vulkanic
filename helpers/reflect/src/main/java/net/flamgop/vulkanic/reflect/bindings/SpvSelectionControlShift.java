package net.flamgop.vulkanic.reflect.bindings;

public enum SpvSelectionControlShift {
    SpvSelectionControlFlattenShift(0),
    SpvSelectionControlDontFlattenShift(1),
    SpvSelectionControlMax(2147483647);

    private final int value;

    SpvSelectionControlShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvSelectionControlShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvSelectionControlShift value: " + value);
    }
}
