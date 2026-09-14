package net.flamgop.vulkanic.core.debug;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.EXTDebugUtils;

public enum VulkanicDebugMessageSeverityFlag implements Bitmaskable<Integer> {
    VERBOSE(EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT),
    INFO(EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT),
    WARNING(EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT),
    ERROR(EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT),
    ;

    private final int flag;
    VulkanicDebugMessageSeverityFlag(int value) {
        this.flag = value;
    }

    @Override
    public Integer flag() {
        return flag;
    }

    public static VulkanicDebugMessageSeverityFlag valueOf(int qualifier) {
        return switch (qualifier) {
            case EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT -> VERBOSE;
            case EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT -> INFO;
            case EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT -> WARNING;
            case EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT -> ERROR;
            default -> throw new IllegalArgumentException("Bad qualifier");
        };
    }
}
