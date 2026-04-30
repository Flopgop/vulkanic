package net.flamgop.vulkanic.memory.image;

import org.lwjgl.vulkan.VK10;

public enum VulkanicSwizzle {
    IDENTITY(VK10.VK_COMPONENT_SWIZZLE_IDENTITY),
    ZERO(VK10.VK_COMPONENT_SWIZZLE_ZERO),
    ONE(VK10.VK_COMPONENT_SWIZZLE_ONE),
    R(VK10.VK_COMPONENT_SWIZZLE_R),
    G(VK10.VK_COMPONENT_SWIZZLE_G),
    B(VK10.VK_COMPONENT_SWIZZLE_B),
    A(VK10.VK_COMPONENT_SWIZZLE_A),
    ;

    private final int qualifier;
    VulkanicSwizzle(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
