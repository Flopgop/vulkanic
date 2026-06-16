package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.memory.VulkanicMemoryHeap;
import net.flamgop.vulkanic.memory.VulkanicMemoryType;

import java.util.List;

public record VulkanicPhysicalDeviceMemoryProperties(
        List<VulkanicMemoryType> memoryTypes,
        List<VulkanicMemoryHeap> memoryHeaps
) {
    public VulkanicPhysicalDeviceMemoryProperties(List<VulkanicMemoryType> memoryTypes,
                                                  List<VulkanicMemoryHeap> memoryHeaps) {
        this.memoryTypes = List.copyOf(memoryTypes);
        this.memoryHeaps = List.copyOf(memoryHeaps);
    }
}
