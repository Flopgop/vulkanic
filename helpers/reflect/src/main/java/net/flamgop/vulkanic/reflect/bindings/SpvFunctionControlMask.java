package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvFunctionControlMask implements Bitmaskable<Integer> {
    SpvFunctionControlMaskNone(0x00000000),
    SpvFunctionControlInlineMask(0x00000001),
    SpvFunctionControlDontInlineMask(0x00000002),
    SpvFunctionControlPureMask(0x00000004),
    SpvFunctionControlConstMask(0x00000008),
    SpvFunctionControlOptNoneEXTMask(0x00010000),
    SpvFunctionControlOptNoneINTELMask(0x00010000);

    private final Integer flag;

    SpvFunctionControlMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
