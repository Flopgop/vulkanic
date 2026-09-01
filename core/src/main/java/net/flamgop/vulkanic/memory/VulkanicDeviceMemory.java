package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public final class VulkanicDeviceMemory implements AutoCloseable, VulkanicObject.Opaque {

    private final VulkanicDevice device;
    private final long handle;

    @ApiStatus.Internal
    public VulkanicDeviceMemory(@NotNull VulkanicDevice device, long handle) {
        this.device = device;
        this.handle = handle;
    }

    @Override
    public void close() {
        device.freeMemory(this);
    }

    @Override
    public long handle() {
        return handle;
    }

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.DEVICE_MEMORY;
    }
}
