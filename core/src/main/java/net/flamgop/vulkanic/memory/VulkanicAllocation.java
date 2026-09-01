package net.flamgop.vulkanic.memory;

public interface VulkanicAllocation {
    VulkanicDeviceMemory deviceMemory();
    VulkanicDeviceSize offset();
    VulkanicDeviceSize size();
    int memoryTypeIndex();
}
