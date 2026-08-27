package net.flamgop.vulkanic.core;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VK10;

import java.util.HashMap;
import java.util.Map;

public enum VulkanicDeviceType {
    /// A non-standard Vulkan device (e.g., a special accelerator or custom hardware)
    OTHER(VK10.VK_PHYSICAL_DEVICE_TYPE_OTHER),
    /// An integrated GPU
    INTEGRATED_GPU(VK10.VK_PHYSICAL_DEVICE_TYPE_INTEGRATED_GPU),
    /// A discrete GPU
    DISCRETE_GPU(VK10.VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU),
    /// A virtual GPU (usually some special vGPU managed by a hypervisor)
    VIRTUAL_GPU(VK10.VK_PHYSICAL_DEVICE_TYPE_VIRTUAL_GPU),
    /// A CPU (compute only or emulated graphics)
    CPU(VK10.VK_PHYSICAL_DEVICE_TYPE_CPU)
    ;
    private static final Map<Integer, VulkanicDeviceType> LOOKUP = new HashMap<>();
    static {
        for (VulkanicDeviceType type : values()) {
            LOOKUP.put(type.qualifier, type);
        }
    }

    private final int qualifier;
    VulkanicDeviceType(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }

    public static @NotNull VulkanicDeviceType valueOf(int value) {
        return LOOKUP.get(value);
    }
}
