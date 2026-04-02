package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.ANDROIDExternalFormatResolve;
import org.lwjgl.vulkan.EXTCustomResolve;
import org.lwjgl.vulkan.VK12;

public enum VulkanicResolveModeFlag implements Bitmaskable<Integer> {
    NONE(VK12.VK_RESOLVE_MODE_NONE),
    SAMPLE_ZERO(VK12.VK_RESOLVE_MODE_SAMPLE_ZERO_BIT),
    AVERAGE(VK12.VK_RESOLVE_MODE_AVERAGE_BIT),
    MIN(VK12.VK_RESOLVE_MODE_MIN_BIT),
    MAX(VK12.VK_RESOLVE_MODE_MAX_BIT),

    EXTERNAL_FORMAT_DOWNSAMPLE_ANDROID(ANDROIDExternalFormatResolve.VK_RESOLVE_MODE_EXTERNAL_FORMAT_DOWNSAMPLE_BIT_ANDROID),

    CUSTOM_EXT(EXTCustomResolve.VK_RENDERING_CUSTOM_RESOLVE_BIT_EXT),

    ;

    private final int flag;
    VulkanicResolveModeFlag(int value) {
        this.flag = value;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
