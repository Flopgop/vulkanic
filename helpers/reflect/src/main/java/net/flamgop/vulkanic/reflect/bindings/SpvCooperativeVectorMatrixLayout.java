package net.flamgop.vulkanic.reflect.bindings;

public enum SpvCooperativeVectorMatrixLayout {
    SpvCooperativeVectorMatrixLayoutRowMajorNV(0),
    SpvCooperativeVectorMatrixLayoutColumnMajorNV(1),
    SpvCooperativeVectorMatrixLayoutInferencingOptimalNV(2),
    SpvCooperativeVectorMatrixLayoutTrainingOptimalNV(3),
    SpvCooperativeVectorMatrixLayoutMax(2147483647);

    private final int value;

    SpvCooperativeVectorMatrixLayout(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvCooperativeVectorMatrixLayout fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvCooperativeVectorMatrixLayout value: " + value);
    }
}
