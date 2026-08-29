package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvMemoryAccessMask implements Bitmaskable<Integer> {
    SpvMemoryAccessMaskNone(0x00000000),
    SpvMemoryAccessVolatileMask(0x00000001),
    SpvMemoryAccessAlignedMask(0x00000002),
    SpvMemoryAccessNontemporalMask(0x00000004),
    SpvMemoryAccessMakePointerAvailableMask(0x00000008),
    SpvMemoryAccessMakePointerAvailableKHRMask(0x00000008),
    SpvMemoryAccessMakePointerVisibleMask(0x00000010),
    SpvMemoryAccessMakePointerVisibleKHRMask(0x00000010),
    SpvMemoryAccessNonPrivatePointerMask(0x00000020),
    SpvMemoryAccessNonPrivatePointerKHRMask(0x00000020),
    SpvMemoryAccessAliasScopeINTELMaskMask(0x00010000),
    SpvMemoryAccessNoAliasINTELMaskMask(0x00020000);

    private final Integer flag;

    SpvMemoryAccessMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
