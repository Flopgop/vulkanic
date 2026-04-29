package net.flamgop.vulkanic.pipeline.descriptor.heap;

import net.flamgop.vulkanic.memory.image.sampler.VulkanicSamplerCreateInfo;
import org.jetbrains.annotations.NotNull;

public sealed interface VulkanicDescriptorMappingSourceData {

    record ConstantOffset(
            int heapOffset,
            int heapArrayStride,
            @NotNull VulkanicSamplerCreateInfo embeddedSampler,
            int samplerHeapOffset,
            int samplerHeapArrayStride
    ) implements VulkanicDescriptorMappingSourceData {}

    record PushIndex(
            int heapOffset,
            int pushOffset,
            int heapIndexStride,
            int heapArrayStride,
            @NotNull VulkanicSamplerCreateInfo embeddedSampler,
            boolean useCombinedImageSamplerIndex,
            int samplerHeapOffset,
            int samplerPushOffset,
            int samplerHeapIndexStride,
            int samplerHeapArrayStride
    ) implements VulkanicDescriptorMappingSourceData {}

    record IndirectIndex(
            int heapOffset,
            int pushOffset,
            int addressOffset,
            int heapIndexStride,
            int heapArrayStride,
            @NotNull VulkanicSamplerCreateInfo embeddedSampler,
            boolean useCombinedImageSamplerIndex,
            int samplerHeapOffset,
            int samplerPushOffset,
            int samplerAddressOffset,
            int samplerHeapIndexStride,
            int samplerHeapArrayStride
    ) implements VulkanicDescriptorMappingSourceData {}

    record IndirectIndexArray(
            int heapOffset,
            int pushOffset,
            int addressOffset,
            int heapIndexStride,
            @NotNull VulkanicSamplerCreateInfo embeddedSampler,
            boolean useCombinedImageSamplerIndex,
            int samplerHeapOffset,
            int samplerPushOffset,
            int samplerAddressOffset,
            int samplerHeapIndexStride
    ) implements VulkanicDescriptorMappingSourceData {}

    record HeapData(
            int heapOffset,
            int pushOffset
    ) implements VulkanicDescriptorMappingSourceData {}

    record PushDataOffset(int value) implements VulkanicDescriptorMappingSourceData {}

    record PushAddressOffset(int value) implements VulkanicDescriptorMappingSourceData {}

    record IndirectAddress(
            int pushOffset,
            int addressOffset
    ) implements VulkanicDescriptorMappingSourceData {}

    record ShaderRecordIndex(
            int heapOffset,
            int shaderRecordOffset,
            int heapIndexStride,
            int heapArrayStride,
            @NotNull VulkanicSamplerCreateInfo embeddedSampler,
            boolean useCombinedImageSamplerIndex,
            int samplerHeapOffset,
            int samplerShaderRecordOffset,
            int samplerHeapIndexStride,
            int samplerHeapArrayStride
    ) implements VulkanicDescriptorMappingSourceData {}

    record ShaderRecordDataOffset(int value) implements VulkanicDescriptorMappingSourceData {}

    record ShaderRecordAddressOffset(int value) implements VulkanicDescriptorMappingSourceData {}
}
