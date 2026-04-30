package net.flamgop.vulkanic.memory.image;

import org.lwjgl.vulkan.VK10;

public enum VulkanicImageType {
    TYPE_1D(VK10.VK_IMAGE_TYPE_1D),
    TYPE_2D(VK10.VK_IMAGE_TYPE_2D),
    TYPE_3D(VK10.VK_IMAGE_TYPE_3D),
    ;

    private final int qualifier;
    VulkanicImageType(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
