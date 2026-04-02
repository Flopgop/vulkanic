package net.flamgop.vulkanic.memory.image;

import org.lwjgl.vulkan.EXTImageDrmFormatModifier;
import org.lwjgl.vulkan.VK10;

public enum VulkanicImageTiling {
    OPTIMAL(VK10.VK_IMAGE_TILING_OPTIMAL),
    LINEAR(VK10.VK_IMAGE_TILING_LINEAR),
    DRM_FORMAT_MODIFIER_EXT(EXTImageDrmFormatModifier.VK_IMAGE_TILING_DRM_FORMAT_MODIFIER_EXT),

    ;
    private final int qualifier;
    VulkanicImageTiling(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
