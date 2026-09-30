package net.flamgop.vulkanic.core.fault;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.EXTDeviceFault;

import java.util.HashMap;
import java.util.Map;

public enum VulkanicDeviceFaultAddressType {
    NONE(EXTDeviceFault.VK_DEVICE_FAULT_ADDRESS_TYPE_NONE_EXT),
    READ_INVALID(EXTDeviceFault.VK_DEVICE_FAULT_ADDRESS_TYPE_READ_INVALID_EXT),
    WRITE_INVALID(EXTDeviceFault.VK_DEVICE_FAULT_ADDRESS_TYPE_WRITE_INVALID_EXT),
    EXECUTE_INVALID(EXTDeviceFault.VK_DEVICE_FAULT_ADDRESS_TYPE_EXECUTE_INVALID_EXT),
    INSTRUCTION_POINTER_UNKNOWN(EXTDeviceFault.VK_DEVICE_FAULT_ADDRESS_TYPE_INSTRUCTION_POINTER_UNKNOWN_EXT),
    INSTRUCTION_POINTER_INVALID(EXTDeviceFault.VK_DEVICE_FAULT_ADDRESS_TYPE_INSTRUCTION_POINTER_INVALID_EXT),
    INSTRUCTION_POINTER_FAULT(EXTDeviceFault.VK_DEVICE_FAULT_ADDRESS_TYPE_INSTRUCTION_POINTER_FAULT_EXT)
    ;
    private static final Map<Integer, VulkanicDeviceFaultAddressType> LOOKUP = new HashMap<>();

    static {
        for (VulkanicDeviceFaultAddressType value : VulkanicDeviceFaultAddressType.values()) {
            LOOKUP.put(value.qualifier(), value);
        }
    }

    public static @NotNull VulkanicDeviceFaultAddressType valueOf(int result) {
        if (!LOOKUP.containsKey(result)) throw new RuntimeException(String.format("BAD DEVICE FAULT ADDRESS TYPE INPUT!! %s", result));
        return LOOKUP.get(result);
    }

    private final int qualifier;
    VulkanicDeviceFaultAddressType(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }
}
