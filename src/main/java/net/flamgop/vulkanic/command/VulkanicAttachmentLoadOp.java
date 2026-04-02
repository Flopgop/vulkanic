package net.flamgop.vulkanic.command;

import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK14;

public enum VulkanicAttachmentLoadOp {
    LOAD(VK10.VK_ATTACHMENT_LOAD_OP_LOAD),
    CLEAR(VK10.VK_ATTACHMENT_LOAD_OP_CLEAR),
    DONT_CARE(VK10.VK_ATTACHMENT_LOAD_OP_DONT_CARE),

    NONE(VK14.VK_ATTACHMENT_LOAD_OP_NONE)

    ;
    private final int qualifier;
    VulkanicAttachmentLoadOp(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }
}
