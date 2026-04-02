package net.flamgop.vulkanic.memory.image;

import org.lwjgl.vulkan.VK10;

public enum VulkanicSharingMode {
    EXCLUSIVE(VK10.VK_SHARING_MODE_EXCLUSIVE),
    CONCURRENT(VK10.VK_SHARING_MODE_CONCURRENT)
    ;

    private final int qualifier;
    VulkanicSharingMode(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
