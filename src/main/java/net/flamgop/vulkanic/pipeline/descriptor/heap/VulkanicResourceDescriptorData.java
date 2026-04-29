package net.flamgop.vulkanic.pipeline.descriptor.heap;

import net.flamgop.vulkanic.memory.VulkanicFormat;
import net.flamgop.vulkanic.memory.image.VulkanicImageLayout;
import net.flamgop.vulkanic.memory.image.VulkanicImageViewCreateInfo;
import org.jetbrains.annotations.NotNull;

public sealed interface VulkanicResourceDescriptorData permits VulkanicDeviceAddressRange, VulkanicResourceDescriptorData.Image, VulkanicResourceDescriptorData.TensorViewCreateInfo, VulkanicResourceDescriptorData.TexelBuffer {
    record Image(
            @NotNull VulkanicImageViewCreateInfo view,
            @NotNull VulkanicImageLayout layout
    ) implements VulkanicResourceDescriptorData {}
    record TexelBuffer(
            @NotNull VulkanicFormat format,
            @NotNull VulkanicDeviceAddressRange addressRange
    ) implements VulkanicResourceDescriptorData {}
    record TensorViewCreateInfo() implements VulkanicResourceDescriptorData {}
}
