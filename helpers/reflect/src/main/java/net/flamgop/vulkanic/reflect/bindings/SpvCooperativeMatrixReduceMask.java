package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvCooperativeMatrixReduceMask implements Bitmaskable<Integer> {
    SpvCooperativeMatrixReduceMaskNone(0x00000000),
    SpvCooperativeMatrixReduceRowMask(0x00000001),
    SpvCooperativeMatrixReduceColumnMask(0x00000002),
    SpvCooperativeMatrixReduce2x2Mask(0x00000004);

    private final Integer flag;

    SpvCooperativeMatrixReduceMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
