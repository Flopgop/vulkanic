package net.flamgop.vulkanic.sync;

import org.lwjgl.vulkan.VK12;

public enum VulkanicSemaphoreType {
    BINARY(VK12.VK_SEMAPHORE_TYPE_BINARY),
    TIMELINE(VK12.VK_SEMAPHORE_TYPE_TIMELINE),

    ;
    private final int qualifier;
    VulkanicSemaphoreType(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }
}
