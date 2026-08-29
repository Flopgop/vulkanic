package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvKernelProfilingInfoMask implements Bitmaskable<Integer> {
    SpvKernelProfilingInfoMaskNone(0x00000000),
    SpvKernelProfilingInfoCmdExecTimeMask(0x00000001);

    private final Integer flag;

    SpvKernelProfilingInfoMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
