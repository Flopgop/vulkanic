package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.EXTMutableDescriptorType;
import org.lwjgl.vulkan.NVDescriptorPoolOverallocation;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK12;

public enum VulkanicDescriptorPoolCreateFlag implements Bitmaskable<Integer> {
    FREE_DESCRIPTOR_SET(VK10.VK_DESCRIPTOR_POOL_CREATE_FREE_DESCRIPTOR_SET_BIT),
    UPDATE_AFTER_BIND(VK12.VK_DESCRIPTOR_POOL_CREATE_UPDATE_AFTER_BIND_BIT),

    HOST_ONLY_EXT(EXTMutableDescriptorType.VK_DESCRIPTOR_POOL_CREATE_HOST_ONLY_BIT_EXT),

    ALLOW_OVERALLOCATION_SETS_NV(NVDescriptorPoolOverallocation.VK_DESCRIPTOR_POOL_CREATE_ALLOW_OVERALLOCATION_SETS_BIT_NV),
    ALLOW_OVERALLOCATION_POOLS_NV(NVDescriptorPoolOverallocation.VK_DESCRIPTOR_POOL_CREATE_ALLOW_OVERALLOCATION_POOLS_BIT_NV)
    ;
    private final int flag;
    VulkanicDescriptorPoolCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
