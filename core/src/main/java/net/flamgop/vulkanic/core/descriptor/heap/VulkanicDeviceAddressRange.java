package net.flamgop.vulkanic.core.descriptor.heap;

import net.flamgop.vulkanic.memory.VulkanicDeviceSize;

public record VulkanicDeviceAddressRange(long address, VulkanicDeviceSize size) implements VulkanicResourceDescriptorData {
}
