package net.flamgop.vulkanic.pipeline.graphics;

import org.lwjgl.vulkan.VK10;

public enum VulkanicFrontFace {
    COUNTER_CLOCKWISE(VK10.VK_FRONT_FACE_COUNTER_CLOCKWISE),
    CLOCKWISE(VK10.VK_FRONT_FACE_CLOCKWISE),
    ;

    private final int qualifier;
    VulkanicFrontFace(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
