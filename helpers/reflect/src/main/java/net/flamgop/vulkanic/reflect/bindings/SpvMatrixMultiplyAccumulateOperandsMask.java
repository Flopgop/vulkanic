package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvMatrixMultiplyAccumulateOperandsMask implements Bitmaskable<Integer> {
    SpvMatrixMultiplyAccumulateOperandsMaskNone(0x00000000),
    SpvMatrixMultiplyAccumulateOperandsMatrixASignedComponentsINTELMask(0x00000001),
    SpvMatrixMultiplyAccumulateOperandsMatrixBSignedComponentsINTELMask(0x00000002),
    SpvMatrixMultiplyAccumulateOperandsMatrixCBFloat16INTELMask(0x00000004),
    SpvMatrixMultiplyAccumulateOperandsMatrixResultBFloat16INTELMask(0x00000008),
    SpvMatrixMultiplyAccumulateOperandsMatrixAPackedInt8INTELMask(0x00000010),
    SpvMatrixMultiplyAccumulateOperandsMatrixBPackedInt8INTELMask(0x00000020),
    SpvMatrixMultiplyAccumulateOperandsMatrixAPackedInt4INTELMask(0x00000040),
    SpvMatrixMultiplyAccumulateOperandsMatrixBPackedInt4INTELMask(0x00000080),
    SpvMatrixMultiplyAccumulateOperandsMatrixATF32INTELMask(0x00000100),
    SpvMatrixMultiplyAccumulateOperandsMatrixBTF32INTELMask(0x00000200),
    SpvMatrixMultiplyAccumulateOperandsMatrixAPackedFloat16INTELMask(0x00000400),
    SpvMatrixMultiplyAccumulateOperandsMatrixBPackedFloat16INTELMask(0x00000800),
    SpvMatrixMultiplyAccumulateOperandsMatrixAPackedBFloat16INTELMask(0x00001000),
    SpvMatrixMultiplyAccumulateOperandsMatrixBPackedBFloat16INTELMask(0x00002000);

    private final Integer flag;

    SpvMatrixMultiplyAccumulateOperandsMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
