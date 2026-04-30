package net.flamgop.vulkanic.command;

import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK13;

public enum VulkanicAttachmentStoreOp {
    STORE(VK10.VK_ATTACHMENT_STORE_OP_STORE),
    DONT_CARE(VK10.VK_ATTACHMENT_STORE_OP_DONT_CARE),

    NONE(VK13.VK_ATTACHMENT_STORE_OP_NONE),

    ;

    private final int qualifier;
    VulkanicAttachmentStoreOp(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }
}
