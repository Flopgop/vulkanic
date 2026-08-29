package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvFragmentShadingRateMask implements Bitmaskable<Integer> {
    SpvFragmentShadingRateMaskNone(0x00000000),
    SpvFragmentShadingRateVertical2PixelsMask(0x00000001),
    SpvFragmentShadingRateVertical4PixelsMask(0x00000002),
    SpvFragmentShadingRateHorizontal2PixelsMask(0x00000004),
    SpvFragmentShadingRateHorizontal4PixelsMask(0x00000008);

    private final Integer flag;

    SpvFragmentShadingRateMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
