package net.flamgop.vulkanic.core.debug;

import net.flamgop.vulkanic.core.VulkanicObjectType;

public record VulkanicDebugObjectNameInfo(
        VulkanicObjectType objectType,
        long objectHandle,
        String objectName
) {
}
