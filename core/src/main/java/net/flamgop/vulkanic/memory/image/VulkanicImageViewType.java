package net.flamgop.vulkanic.memory.image;

import org.lwjgl.vulkan.VK10;

public enum VulkanicImageViewType {
    TYPE_1D(VK10.VK_IMAGE_VIEW_TYPE_1D),
    TYPE_2D(VK10.VK_IMAGE_VIEW_TYPE_2D),
    TYPE_3D(VK10.VK_IMAGE_VIEW_TYPE_3D),
    TYPE_CUBE(VK10.VK_IMAGE_VIEW_TYPE_CUBE),
    TYPE_1D_ARRAY(VK10.VK_IMAGE_VIEW_TYPE_1D_ARRAY),
    TYPE_2D_ARRAY(VK10.VK_IMAGE_VIEW_TYPE_2D_ARRAY),
    TYPE_CUBE_ARRAY(VK10.VK_IMAGE_VIEW_TYPE_CUBE_ARRAY),
    ;

    private final int qualifier;
    VulkanicImageViewType(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
