package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvTensorAddressingOperandsMask implements Bitmaskable<Integer> {
    SpvTensorAddressingOperandsMaskNone(0x00000000),
    SpvTensorAddressingOperandsTensorViewMask(0x00000001),
    SpvTensorAddressingOperandsDecodeFuncMask(0x00000002),
    SpvTensorAddressingOperandsDecodeVectorFuncMask(0x00000004);

    private final Integer flag;

    SpvTensorAddressingOperandsMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
