package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvFPFastMathModeMask implements Bitmaskable<Integer> {
    SpvFPFastMathModeMaskNone(0x00000000),
    SpvFPFastMathModeNotNaNMask(0x00000001),
    SpvFPFastMathModeNotInfMask(0x00000002),
    SpvFPFastMathModeNSZMask(0x00000004),
    SpvFPFastMathModeAllowRecipMask(0x00000008),
    SpvFPFastMathModeFastMask(0x00000010),
    SpvFPFastMathModeAllowContractMask(0x00010000),
    SpvFPFastMathModeAllowContractFastINTELMask(0x00010000),
    SpvFPFastMathModeAllowReassocMask(0x00020000),
    SpvFPFastMathModeAllowReassocINTELMask(0x00020000),
    SpvFPFastMathModeAllowTransformMask(0x00040000);

    private final Integer flag;

    SpvFPFastMathModeMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
