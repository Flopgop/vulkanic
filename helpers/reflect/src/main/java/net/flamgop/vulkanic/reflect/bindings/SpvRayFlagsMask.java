package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvRayFlagsMask implements Bitmaskable<Integer> {
    SpvRayFlagsMaskNone(0x00000000),
    SpvRayFlagsOpaqueKHRMask(0x00000001),
    SpvRayFlagsNoOpaqueKHRMask(0x00000002),
    SpvRayFlagsTerminateOnFirstHitKHRMask(0x00000004),
    SpvRayFlagsSkipClosestHitShaderKHRMask(0x00000008),
    SpvRayFlagsCullBackFacingTrianglesKHRMask(0x00000010),
    SpvRayFlagsCullFrontFacingTrianglesKHRMask(0x00000020),
    SpvRayFlagsCullOpaqueKHRMask(0x00000040),
    SpvRayFlagsCullNoOpaqueKHRMask(0x00000080),
    SpvRayFlagsSkipBuiltinPrimitivesNVMask(0x00000100),
    SpvRayFlagsSkipTrianglesKHRMask(0x00000100),
    SpvRayFlagsSkipAABBsKHRMask(0x00000200),
    SpvRayFlagsForceOpacityMicromap2StateEXTMask(0x00000400),
    SpvRayFlagsForceOpacityMicromap2StateKHRMask(0x00000400);

    private final Integer flag;

    SpvRayFlagsMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
