package net.flamgop.vulkanic.debug;

import net.flamgop.vulkanic.core.debug.VulkanicDebugMessageSeverityFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;

public class DebugHelpers {
    public static VulkanicDebugMessageSeverityFlag highestSeverity(EnumIntBitset<VulkanicDebugMessageSeverityFlag> severity) {
        if (severity.contains(VulkanicDebugMessageSeverityFlag.ERROR)) {
            return VulkanicDebugMessageSeverityFlag.ERROR;
        } else if (severity.contains(VulkanicDebugMessageSeverityFlag.WARNING)) {
            return VulkanicDebugMessageSeverityFlag.WARNING;
        } else if (severity.contains(VulkanicDebugMessageSeverityFlag.INFO)) {
            return VulkanicDebugMessageSeverityFlag.INFO;
        } else {
            return VulkanicDebugMessageSeverityFlag.VERBOSE;
        }
    }
}
