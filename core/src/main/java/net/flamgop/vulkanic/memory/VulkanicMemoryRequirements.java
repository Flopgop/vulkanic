package net.flamgop.vulkanic.memory;

public record VulkanicMemoryRequirements(
        VulkanicDeviceSize size,
        VulkanicDeviceSize alignment,
        int memoryTypeBits
) {
}
