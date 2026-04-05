package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.*;

public enum VulkanicDescriptorSetLayoutCreateFlag implements Bitmaskable<Integer> {
    UPDATE_AFTER_BIND_POOL(VK12.VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT),

    PUSH_DESCRIPTOR(VK14.VK_DESCRIPTOR_SET_LAYOUT_CREATE_PUSH_DESCRIPTOR_BIT),

    DESCRIPTOR_BUFFER_EXT(EXTDescriptorBuffer.VK_DESCRIPTOR_SET_LAYOUT_CREATE_DESCRIPTOR_BUFFER_BIT_EXT),
    EMBEDDED_IMMUTABLE_SAMPLERS_EXT(EXTDescriptorBuffer.VK_DESCRIPTOR_SET_LAYOUT_CREATE_EMBEDDED_IMMUTABLE_SAMPLERS_BIT_EXT),

    INDIRECT_BINDABLE_NV(NVDeviceGeneratedCommandsCompute.VK_DESCRIPTOR_SET_LAYOUT_CREATE_INDIRECT_BINDABLE_BIT_NV),

    HOST_ONLY_POOL_EXT(EXTMutableDescriptorType.VK_DESCRIPTOR_SET_LAYOUT_CREATE_HOST_ONLY_POOL_BIT_EXT),

    PER_STAGE_NV(NVPerStageDescriptorSet.VK_DESCRIPTOR_SET_LAYOUT_CREATE_PER_STAGE_BIT_NV),
    ;

    private final int flag;
    VulkanicDescriptorSetLayoutCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
