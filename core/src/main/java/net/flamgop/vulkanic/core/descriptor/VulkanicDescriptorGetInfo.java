package net.flamgop.vulkanic.core.descriptor;

public record VulkanicDescriptorGetInfo(
        VulkanicDescriptorData data // data is automatically specialized from the sealed interface :3
) {
    public VulkanicDescriptorType type() {
        return switch (data) {
            case VulkanicDescriptorData.Sampler _ -> VulkanicDescriptorType.SAMPLER;
            case VulkanicDescriptorData.CombinedImageSampler _ -> VulkanicDescriptorType.COMBINED_IMAGE_SAMPLER;
            case VulkanicDescriptorData.InputAttachmentImage _ -> VulkanicDescriptorType.INPUT_ATTACHMENT;
            case VulkanicDescriptorData.SampledImage _ -> VulkanicDescriptorType.SAMPLED_IMAGE;
            case VulkanicDescriptorData.StorageImage _ -> VulkanicDescriptorType.STORAGE_IMAGE;
            case VulkanicDescriptorData.UniformTexelBuffer _ -> VulkanicDescriptorType.UNIFORM_TEXEL_BUFFER;
            case VulkanicDescriptorData.StorageTexelBuffer _ -> VulkanicDescriptorType.STORAGE_TEXEL_BUFFER;
            case VulkanicDescriptorData.UniformBuffer _ -> VulkanicDescriptorType.UNIFORM_BUFFER;
            case VulkanicDescriptorData.StorageBuffer _ -> VulkanicDescriptorType.STORAGE_BUFFER;
            case VulkanicDescriptorData.AccelerationStructureKHR _ -> VulkanicDescriptorType.ACCELERATION_STRUCTURE_KHR;
            case VulkanicDescriptorData.AccelerationStructureNV _ -> VulkanicDescriptorType.ACCELERATION_STRUCTURE_NV;
        };
    }
}
