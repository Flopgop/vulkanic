package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvTensorOperandsMask implements Bitmaskable<Integer> {
    SpvTensorOperandsMaskNone(0x00000000),
    SpvTensorOperandsNontemporalARMMask(0x00000001),
    SpvTensorOperandsOutOfBoundsValueARMMask(0x00000002),
    SpvTensorOperandsMakeElementAvailableARMMask(0x00000004),
    SpvTensorOperandsMakeElementVisibleARMMask(0x00000008),
    SpvTensorOperandsNonPrivateElementARMMask(0x00000010);

    private final Integer flag;

    SpvTensorOperandsMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
