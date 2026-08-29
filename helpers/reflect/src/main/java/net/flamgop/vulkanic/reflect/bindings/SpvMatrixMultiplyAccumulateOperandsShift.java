package net.flamgop.vulkanic.reflect.bindings;

public enum SpvMatrixMultiplyAccumulateOperandsShift {
    SpvMatrixMultiplyAccumulateOperandsMatrixASignedComponentsINTELShift(0),
    SpvMatrixMultiplyAccumulateOperandsMatrixBSignedComponentsINTELShift(1),
    SpvMatrixMultiplyAccumulateOperandsMatrixCBFloat16INTELShift(2),
    SpvMatrixMultiplyAccumulateOperandsMatrixResultBFloat16INTELShift(3),
    SpvMatrixMultiplyAccumulateOperandsMatrixAPackedInt8INTELShift(4),
    SpvMatrixMultiplyAccumulateOperandsMatrixBPackedInt8INTELShift(5),
    SpvMatrixMultiplyAccumulateOperandsMatrixAPackedInt4INTELShift(6),
    SpvMatrixMultiplyAccumulateOperandsMatrixBPackedInt4INTELShift(7),
    SpvMatrixMultiplyAccumulateOperandsMatrixATF32INTELShift(8),
    SpvMatrixMultiplyAccumulateOperandsMatrixBTF32INTELShift(9),
    SpvMatrixMultiplyAccumulateOperandsMatrixAPackedFloat16INTELShift(10),
    SpvMatrixMultiplyAccumulateOperandsMatrixBPackedFloat16INTELShift(11),
    SpvMatrixMultiplyAccumulateOperandsMatrixAPackedBFloat16INTELShift(12),
    SpvMatrixMultiplyAccumulateOperandsMatrixBPackedBFloat16INTELShift(13),
    SpvMatrixMultiplyAccumulateOperandsMax(2147483647);

    private final int value;

    SpvMatrixMultiplyAccumulateOperandsShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvMatrixMultiplyAccumulateOperandsShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvMatrixMultiplyAccumulateOperandsShift value: " + value);
    }
}
