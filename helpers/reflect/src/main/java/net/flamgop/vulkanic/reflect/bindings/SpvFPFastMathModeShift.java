package net.flamgop.vulkanic.reflect.bindings;

public enum SpvFPFastMathModeShift {
    SpvFPFastMathModeNotNaNShift(0),
    SpvFPFastMathModeNotInfShift(1),
    SpvFPFastMathModeNSZShift(2),
    SpvFPFastMathModeAllowRecipShift(3),
    SpvFPFastMathModeFastShift(4),
    SpvFPFastMathModeAllowContractShift(16),
    SpvFPFastMathModeAllowContractFastINTELShift(16),
    SpvFPFastMathModeAllowReassocShift(17),
    SpvFPFastMathModeAllowReassocINTELShift(17),
    SpvFPFastMathModeAllowTransformShift(18),
    SpvFPFastMathModeMax(2147483647);

    private final int value;

    SpvFPFastMathModeShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvFPFastMathModeShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvFPFastMathModeShift value: " + value);
    }
}
