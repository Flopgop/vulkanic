package net.flamgop.vulkanic.memory.image.sampler;

import org.lwjgl.vulkan.EXTCustomBorderColor;
import org.lwjgl.vulkan.VK10;

public enum VulkanicBorderColor {
    FLOAT_TRANSPARENT_BLACK(VK10.VK_BORDER_COLOR_FLOAT_TRANSPARENT_BLACK),
    INT_TRANSPARENT_BLACK(VK10.VK_BORDER_COLOR_INT_TRANSPARENT_BLACK),
    FLOAT_OPAQUE_BLACK(VK10.VK_BORDER_COLOR_FLOAT_OPAQUE_BLACK),
    INT_OPAQUE_BLACK(VK10.VK_BORDER_COLOR_INT_OPAQUE_BLACK),
    FLOAT_OPAQUE_WHITE(VK10.VK_BORDER_COLOR_FLOAT_OPAQUE_WHITE),
    INT_OPAQUE_WHITE(VK10.VK_BORDER_COLOR_INT_OPAQUE_WHITE),

    FLOAT_CUSTOM_EXT(EXTCustomBorderColor.VK_BORDER_COLOR_FLOAT_CUSTOM_EXT),
    INT_CUSTOM_EXT(EXTCustomBorderColor.VK_BORDER_COLOR_INT_CUSTOM_EXT),

    ;

    private final int qualifier;
    VulkanicBorderColor(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
