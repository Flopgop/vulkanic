package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvRawAccessChainOperandsMask implements Bitmaskable<Integer> {
    SpvRawAccessChainOperandsMaskNone(0x00000000),
    SpvRawAccessChainOperandsRobustnessPerComponentNVMask(0x00000001),
    SpvRawAccessChainOperandsRobustnessPerElementNVMask(0x00000002);

    private final Integer flag;

    SpvRawAccessChainOperandsMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
