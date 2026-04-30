package net.flamgop.vulkanic.pipeline.descriptor.heap;

import net.flamgop.vulkanic.util.EnumIntBitset;

public record VulkanicDescriptorSetAndBindingMapping(
        int descriptorSet,
        int firstBinding,
        int bindingCount,
        EnumIntBitset<VulkanicSpirvResourceTypeFlag> resourceMask,
        VulkanicDescriptorMappingSource source,
        VulkanicDescriptorMappingSourceData sourceData
) {
}
