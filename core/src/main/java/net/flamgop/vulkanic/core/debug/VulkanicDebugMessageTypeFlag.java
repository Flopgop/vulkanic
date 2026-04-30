package net.flamgop.vulkanic.core.debug;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.EXTDebugUtils;
import org.lwjgl.vulkan.EXTDeviceAddressBindingReport;

public enum VulkanicDebugMessageTypeFlag implements Bitmaskable<Integer> {
    GENERAL(EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT),
    VALIDATION(EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT),
    PERFORMANCE(EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT),

    DEVICE_ADDRESS_BINDING(EXTDeviceAddressBindingReport.VK_DEBUG_UTILS_MESSAGE_TYPE_DEVICE_ADDRESS_BINDING_BIT_EXT),

    ;
    private final int flag;
    VulkanicDebugMessageTypeFlag(int value) {
        this.flag = value;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
