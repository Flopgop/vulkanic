package net.flamgop.vulkanic.reflect.bindings;

public enum SpvDim {
    SpvDim1D(0),
    SpvDim2D(1),
    SpvDim3D(2),
    SpvDimCube(3),
    SpvDimRect(4),
    SpvDimBuffer(5),
    SpvDimSubpassData(6),
    SpvDimTileImageDataEXT(4173),
    SpvDimMax(2147483647);

    private final int value;

    SpvDim(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvDim fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvDim value: " + value);
    }
}
