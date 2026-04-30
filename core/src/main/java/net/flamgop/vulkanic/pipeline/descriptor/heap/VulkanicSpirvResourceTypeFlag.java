package net.flamgop.vulkanic.pipeline.descriptor.heap;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.EXTDescriptorHeap;

public enum VulkanicSpirvResourceTypeFlag implements Bitmaskable<Integer> {
    ALL(EXTDescriptorHeap.VK_SPIRV_RESOURCE_TYPE_ALL_EXT),
    SAMPLER(EXTDescriptorHeap.VK_SPIRV_RESOURCE_TYPE_SAMPLER_BIT_EXT),
    SAMPLED_IMAGE(EXTDescriptorHeap.VK_SPIRV_RESOURCE_TYPE_SAMPLED_IMAGE_BIT_EXT),
    READ_ONLY_IMAGE(EXTDescriptorHeap.VK_SPIRV_RESOURCE_TYPE_READ_ONLY_IMAGE_BIT_EXT),
    READ_WRITE_IMAGE(EXTDescriptorHeap.VK_SPIRV_RESOURCE_TYPE_READ_WRITE_IMAGE_BIT_EXT),
    COMBINED_SAMPLED_IMAGE(EXTDescriptorHeap.VK_SPIRV_RESOURCE_TYPE_COMBINED_SAMPLED_IMAGE_BIT_EXT),
    UNIFORM_BUFFER(EXTDescriptorHeap.VK_SPIRV_RESOURCE_TYPE_UNIFORM_BUFFER_BIT_EXT),
    READ_ONLY_STORAGE_BUFFER(EXTDescriptorHeap.VK_SPIRV_RESOURCE_TYPE_READ_ONLY_STORAGE_BUFFER_BIT_EXT),
    READ_WRITE_STORAGE_BUFFER(EXTDescriptorHeap.VK_SPIRV_RESOURCE_TYPE_READ_WRITE_STORAGE_BUFFER_BIT_EXT),

    ACCELERATION_STRUCTURE_EXT(EXTDescriptorHeap.VK_SPIRV_RESOURCE_TYPE_ACCELERATION_STRUCTURE_BIT_EXT), // VK_KHR_ray_tracing_pipeline

    TENSOR_ARM(EXTDescriptorHeap.VK_SPIRV_RESOURCE_TYPE_TENSOR_BIT_ARM), // VK_ARM_tensors
    ;

    private final int flag;
    VulkanicSpirvResourceTypeFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
