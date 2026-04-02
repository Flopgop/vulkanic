package net.flamgop.vulkanic.pipeline.graphics;

import org.lwjgl.vulkan.VK10;

public enum VulkanicVertexInputRate {
    VERTEX(VK10.VK_VERTEX_INPUT_RATE_VERTEX),
    INSTANCE(VK10.VK_VERTEX_INPUT_RATE_INSTANCE),

    ;

    private final int qualifier;
    VulkanicVertexInputRate(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
