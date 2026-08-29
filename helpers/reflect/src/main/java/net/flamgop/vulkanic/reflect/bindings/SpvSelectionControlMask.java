package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvSelectionControlMask implements Bitmaskable<Integer> {
    SpvSelectionControlMaskNone(0x00000000),
    SpvSelectionControlFlattenMask(0x00000001),
    SpvSelectionControlDontFlattenMask(0x00000002);

    private final Integer flag;

    SpvSelectionControlMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
