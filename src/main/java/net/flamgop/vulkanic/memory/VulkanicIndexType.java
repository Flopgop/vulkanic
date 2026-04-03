package net.flamgop.vulkanic.memory;

import org.lwjgl.vulkan.KHRAccelerationStructure;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK14;

public enum VulkanicIndexType {
    UINT16(VK10.VK_INDEX_TYPE_UINT16),
    UINT32(VK10.VK_INDEX_TYPE_UINT32),

    UINT8(VK14.VK_INDEX_TYPE_UINT8),

    NONE(KHRAccelerationStructure.VK_INDEX_TYPE_NONE_KHR)
    ;
    private final int qualifier;
    VulkanicIndexType(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }
}
