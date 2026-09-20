package net.flamgop.vulkanic.memory;

import org.lwjgl.vulkan.KHRAccelerationStructure;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK14;

public enum VulkanicIndexType {
    UINT16(VK10.VK_INDEX_TYPE_UINT16, 2),
    UINT32(VK10.VK_INDEX_TYPE_UINT32, 4),

    UINT8(VK14.VK_INDEX_TYPE_UINT8, 1),

    NONE(KHRAccelerationStructure.VK_INDEX_TYPE_NONE_KHR, 0)
    ;
    private final int qualifier;
    private final long size;
    VulkanicIndexType(int qualifier, long size) {
        this.qualifier = qualifier;
        this.size = size;
    }

    public long size() {
        return size;
    }
    public int qualifier() {
        return qualifier;
    }
}
