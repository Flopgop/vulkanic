package net.flamgop.vulkanic.core.debug;

import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;

public record VulkanicDebugObjectNameInfo(
        VulkanicObjectType objectType,
        long objectHandle,
        String objectName
) {
    public VulkanicDebugObjectNameInfo(VulkanicObject object, String name) {
        long handle = switch (object) {
            case VulkanicObject.Opaque o -> o.handle();
            case VulkanicObject.Typed<?> t ->  t.handle().address();
        };
        this(object.objectType(), handle, name);
    }
}
