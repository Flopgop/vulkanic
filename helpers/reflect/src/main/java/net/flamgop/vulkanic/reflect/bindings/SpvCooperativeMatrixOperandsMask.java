package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvCooperativeMatrixOperandsMask implements Bitmaskable<Integer> {
    SpvCooperativeMatrixOperandsMaskNone(0x00000000),
    SpvCooperativeMatrixOperandsMatrixASignedComponentsKHRMask(0x00000001),
    SpvCooperativeMatrixOperandsMatrixBSignedComponentsKHRMask(0x00000002),
    SpvCooperativeMatrixOperandsMatrixCSignedComponentsKHRMask(0x00000004),
    SpvCooperativeMatrixOperandsMatrixResultSignedComponentsKHRMask(0x00000008),
    SpvCooperativeMatrixOperandsSaturatingAccumulationKHRMask(0x00000010);

    private final Integer flag;

    SpvCooperativeMatrixOperandsMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
