package net.flamgop.vulkanic.reflect.bindings;

public enum SpvFragmentShadingRateShift {
    SpvFragmentShadingRateVertical2PixelsShift(0),
    SpvFragmentShadingRateVertical4PixelsShift(1),
    SpvFragmentShadingRateHorizontal2PixelsShift(2),
    SpvFragmentShadingRateHorizontal4PixelsShift(3),
    SpvFragmentShadingRateMax(2147483647);

    private final int value;

    SpvFragmentShadingRateShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvFragmentShadingRateShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvFragmentShadingRateShift value: " + value);
    }
}
