package net.flamgop.vulkanic.command;

import org.lwjgl.vulkan.VK10;

public enum VulkanicCommandBufferLevel {
    PRIMARY(VK10.VK_COMMAND_BUFFER_LEVEL_PRIMARY),
    SECONDARY(VK10.VK_COMMAND_BUFFER_LEVEL_SECONDARY)
    ;
    private final int qualifier;
    VulkanicCommandBufferLevel(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }
}
