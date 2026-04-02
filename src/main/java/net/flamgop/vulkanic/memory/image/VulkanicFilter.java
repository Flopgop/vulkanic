package net.flamgop.vulkanic.memory.image;

import org.lwjgl.vulkan.EXTFilterCubic;
import org.lwjgl.vulkan.VK10;

public enum VulkanicFilter {
    NEAREST(VK10.VK_FILTER_NEAREST),
    LINEAR(VK10.VK_FILTER_LINEAR),
    CUBIC_EXT(EXTFilterCubic.VK_FILTER_CUBIC_EXT),
    CUBIC_IMG(CUBIC_EXT),
    ;
    private final int qualifier;
    VulkanicFilter(VulkanicFilter format) {
        this.qualifier = format.qualifier;
    }
    VulkanicFilter(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
