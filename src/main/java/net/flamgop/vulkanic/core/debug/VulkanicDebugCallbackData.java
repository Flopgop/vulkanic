package net.flamgop.vulkanic.core.debug;

import java.util.List;

public record VulkanicDebugCallbackData(
        String messageIdName,
        int messageIdNumber,
        String message,
        List<VulkanicDebugLabel> queueLabels,
        List<VulkanicDebugLabel> commandBufferLabels,
        List<VulkanicDebugObjectNameInfo> objects
) {
}
