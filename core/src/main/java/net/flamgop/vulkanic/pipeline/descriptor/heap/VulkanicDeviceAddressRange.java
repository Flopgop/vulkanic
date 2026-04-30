package net.flamgop.vulkanic.pipeline.descriptor.heap;

import net.flamgop.vulkanic.memory.VulkanicDeviceSize;

public record VulkanicDeviceAddressRange(long address, VulkanicDeviceSize size) implements VulkanicResourceDescriptorData {
}
