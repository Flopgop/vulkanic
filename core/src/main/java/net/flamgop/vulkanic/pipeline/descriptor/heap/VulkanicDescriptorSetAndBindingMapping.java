package net.flamgop.vulkanic.pipeline.descriptor.heap;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkDescriptorMappingSourceDataEXT;
import org.lwjgl.vulkan.VkDescriptorSetAndBindingMappingEXT;
import org.lwjgl.vulkan.VkSamplerCreateInfo;

public record VulkanicDescriptorSetAndBindingMapping(
        int descriptorSet,
        int firstBinding,
        int bindingCount,
        EnumIntBitset<VulkanicSpirvResourceTypeFlag> resourceMask,
        VulkanicDescriptorMappingSource source,
        VulkanicDescriptorMappingSourceData sourceData
) {
    public VkDescriptorSetAndBindingMappingEXT build(VkDescriptorSetAndBindingMappingEXT struct, MemoryStack stack) {
        VkDescriptorMappingSourceDataEXT pSourceData = VkDescriptorMappingSourceDataEXT.calloc(stack);

        switch (sourceData) {
            case VulkanicDescriptorMappingSourceData.ConstantOffset constantOffset -> pSourceData.constantOffset(o ->
                    o
                            .heapOffset(constantOffset.heapOffset())
                            .heapArrayStride(constantOffset.heapArrayStride())
                            .pEmbeddedSampler(constantOffset.embeddedSampler().populate(VkSamplerCreateInfo.calloc(stack)))
                            .samplerHeapOffset(constantOffset.samplerHeapOffset())
                            .samplerHeapArrayStride(constantOffset.samplerHeapArrayStride())
            );
            case VulkanicDescriptorMappingSourceData.PushIndex pushIndex -> pSourceData.pushIndex(i ->
                    i
                            .heapOffset(pushIndex.heapOffset())
                            .pushOffset(pushIndex.pushOffset())
                            .heapIndexStride(pushIndex.heapIndexStride())
                            .heapArrayStride(pushIndex.heapArrayStride())
                            .pEmbeddedSampler(pushIndex.embeddedSampler().populate(VkSamplerCreateInfo.calloc(stack)))
                            .useCombinedImageSamplerIndex(pushIndex.useCombinedImageSamplerIndex())
                            .samplerHeapOffset(pushIndex.samplerHeapOffset())
                            .samplerPushOffset(pushIndex.samplerPushOffset())
                            .samplerHeapIndexStride(pushIndex.samplerHeapIndexStride())
                            .samplerHeapArrayStride(pushIndex.samplerHeapArrayStride())
            );
            case VulkanicDescriptorMappingSourceData.IndirectIndex indirectIndex -> pSourceData.indirectIndex(i ->
                    i
                            .heapOffset(indirectIndex.heapOffset())
                            .pushOffset(indirectIndex.pushOffset())
                            .addressOffset(indirectIndex.addressOffset())
                            .heapIndexStride(indirectIndex.heapIndexStride())
                            .heapArrayStride(indirectIndex.heapArrayStride())
                            .pEmbeddedSampler(indirectIndex.embeddedSampler().populate(VkSamplerCreateInfo.calloc(stack)))
                            .useCombinedImageSamplerIndex(indirectIndex.useCombinedImageSamplerIndex())
                            .samplerHeapOffset(indirectIndex.samplerHeapOffset())
                            .samplerPushOffset(indirectIndex.samplerPushOffset())
                            .samplerAddressOffset(indirectIndex.samplerAddressOffset())
                            .samplerHeapIndexStride(indirectIndex.samplerHeapIndexStride())
                            .samplerHeapArrayStride(indirectIndex.samplerHeapArrayStride())
            );
            case VulkanicDescriptorMappingSourceData.IndirectIndexArray indirectIndexArray -> pSourceData.indirectIndexArray(i ->
                    i
                            .heapOffset(indirectIndexArray.heapOffset())
                            .pushOffset(indirectIndexArray.pushOffset())
                            .addressOffset(indirectIndexArray.addressOffset())
                            .heapIndexStride(indirectIndexArray.heapIndexStride())
                            .pEmbeddedSampler(indirectIndexArray.embeddedSampler().populate(VkSamplerCreateInfo.calloc(stack)))
                            .useCombinedImageSamplerIndex(indirectIndexArray.useCombinedImageSamplerIndex())
                            .samplerHeapOffset(indirectIndexArray.samplerHeapOffset())
                            .samplerPushOffset(indirectIndexArray.samplerPushOffset())
                            .samplerAddressOffset(indirectIndexArray.samplerAddressOffset())
                            .samplerHeapIndexStride(indirectIndexArray.samplerHeapIndexStride())
            );
            case VulkanicDescriptorMappingSourceData.HeapData heapData -> pSourceData.heapData(h ->
                    h
                            .heapOffset(heapData.heapOffset())
                            .pushOffset(heapData.pushOffset())
            );
            case VulkanicDescriptorMappingSourceData.PushDataOffset pushDataOffset -> pSourceData.pushDataOffset(pushDataOffset.value());
            case VulkanicDescriptorMappingSourceData.PushAddressOffset pushAddressOffset -> pSourceData.pushAddressOffset(pushAddressOffset.value());
            case VulkanicDescriptorMappingSourceData.IndirectAddress indirectAddress -> pSourceData.indirectAddress(i ->
                    i
                            .pushOffset(indirectAddress.pushOffset())
                            .addressOffset(indirectAddress.addressOffset())
            );
            case VulkanicDescriptorMappingSourceData.ShaderRecordIndex shaderRecordIndex -> pSourceData.shaderRecordIndex(s ->
                    s
                            .heapOffset(shaderRecordIndex.heapOffset())
                            .shaderRecordOffset(shaderRecordIndex.shaderRecordOffset())
                            .heapIndexStride(shaderRecordIndex.heapIndexStride())
                            .heapArrayStride(shaderRecordIndex.heapArrayStride())
                            .pEmbeddedSampler(shaderRecordIndex.embeddedSampler().populate(VkSamplerCreateInfo.calloc(stack)))
                            .useCombinedImageSamplerIndex(shaderRecordIndex.useCombinedImageSamplerIndex())
                            .samplerHeapOffset(shaderRecordIndex.samplerHeapOffset())
                            .samplerShaderRecordOffset(shaderRecordIndex.samplerShaderRecordOffset())
                            .samplerHeapIndexStride(shaderRecordIndex.samplerHeapIndexStride())
                            .samplerHeapArrayStride(shaderRecordIndex.samplerHeapArrayStride())
            );
            case VulkanicDescriptorMappingSourceData.ShaderRecordDataOffset shaderRecordDataOffset -> pSourceData.shaderRecordDataOffset(shaderRecordDataOffset.value());
            case VulkanicDescriptorMappingSourceData.ShaderRecordAddressOffset shaderRecordAddressOffset -> pSourceData.shaderRecordAddressOffset(shaderRecordAddressOffset.value());
        }

        return struct
                .sType$Default()
                .descriptorSet(descriptorSet())
                .firstBinding(firstBinding())
                .bindingCount(bindingCount())
                .resourceMask(resourceMask().mask())
                .source(source().qualifier())
                .sourceData(pSourceData);
    }
}
