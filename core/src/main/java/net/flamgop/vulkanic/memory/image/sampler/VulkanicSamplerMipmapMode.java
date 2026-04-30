package net.flamgop.vulkanic.memory.image.sampler;


import org.lwjgl.vulkan.VK10;

public enum VulkanicSamplerMipmapMode {
    NEAREST(VK10.VK_SAMPLER_MIPMAP_MODE_NEAREST),
    LINEAR(VK10.VK_SAMPLER_MIPMAP_MODE_LINEAR)
    ;

    private final int qualifier;
    VulkanicSamplerMipmapMode(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
