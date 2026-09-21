package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import org.lwjgl.vulkan.KHRMaintenance7;
import org.lwjgl.vulkan.VK10;

public enum VulkanicSubpassContents {
    INLINE(VK10.VK_SUBPASS_CONTENTS_INLINE),
    SECONDARY_COMMAND_BUFFERS(VK10.VK_SUBPASS_CONTENTS_SECONDARY_COMMAND_BUFFERS),

    INLINE_AND_SECONDARY_COMMAND_BUFFERS_KHR(KHRMaintenance7.VK_SUBPASS_CONTENTS_INLINE_AND_SECONDARY_COMMAND_BUFFERS_KHR)
    ;
    private final int qualifier;
    VulkanicSubpassContents(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }
}
