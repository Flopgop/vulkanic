package net.flamgop.vulkanic.pipeline.graphics;

import org.lwjgl.vulkan.VK10;

public enum VulkanicStencilOp {
    KEEP(VK10.VK_STENCIL_OP_KEEP),
    ZERO(VK10.VK_STENCIL_OP_ZERO),
    REPLACE(VK10.VK_STENCIL_OP_REPLACE),
    INCREMENT_AND_CLAMP(VK10.VK_STENCIL_OP_INCREMENT_AND_CLAMP),
    DECREMENT_AND_CLAMP(VK10.VK_STENCIL_OP_DECREMENT_AND_CLAMP),
    INVERT(VK10.VK_STENCIL_OP_INVERT),
    INCREMENT_AND_WRAP(VK10.VK_STENCIL_OP_INCREMENT_AND_WRAP),
    DECREMENT_AND_WRAP(VK10.VK_STENCIL_OP_DECREMENT_AND_WRAP),
    ;

    private final int qualifier;
    VulkanicStencilOp(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
