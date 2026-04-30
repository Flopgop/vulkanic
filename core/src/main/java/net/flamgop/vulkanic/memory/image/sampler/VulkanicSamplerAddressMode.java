package net.flamgop.vulkanic.memory.image.sampler;

import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK12;

public enum VulkanicSamplerAddressMode {
    REPEAT(VK10.VK_SAMPLER_ADDRESS_MODE_REPEAT),
    MIRRORED_REPEAT(VK10.VK_SAMPLER_ADDRESS_MODE_MIRRORED_REPEAT),
    CLAMP_TO_EDGE(VK10.VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_EDGE),
    CLAMP_TO_BORDER(VK10.VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_BORDER),

    MIRROR_CLAMP_TO_EDGE(VK12.VK_SAMPLER_ADDRESS_MODE_MIRROR_CLAMP_TO_EDGE),

    ;

    private final int qualifier;
    VulkanicSamplerAddressMode(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
