package net.flamgop.vulkanic.pipeline.graphics;

import org.lwjgl.vulkan.VK10;

public enum VulkanicCompareOp {
    NEVER(VK10.VK_COMPARE_OP_NEVER),
    LESS(VK10.VK_COMPARE_OP_LESS),
    EQUAL(VK10.VK_COMPARE_OP_EQUAL),
    LESS_OR_EQUAL(VK10.VK_COMPARE_OP_LESS_OR_EQUAL),
    GREATER(VK10.VK_COMPARE_OP_GREATER),
    NOT_EQUAL(VK10.VK_COMPARE_OP_NOT_EQUAL),
    GREATER_OR_EQUAL(VK10.VK_COMPARE_OP_GREATER_OR_EQUAL),
    ALWAYS(VK10.VK_COMPARE_OP_ALWAYS),
    ;

    private final int qualifier;
    VulkanicCompareOp(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
