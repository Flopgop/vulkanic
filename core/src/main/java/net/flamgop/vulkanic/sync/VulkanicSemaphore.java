package net.flamgop.vulkanic.sync;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import net.flamgop.vulkanic.exception.VulkanicResult;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VulkanicSemaphore implements AutoCloseable, VulkanicObject.Opaque {
    private final VulkanicDevice device;
    private final long handle;
    private final VulkanicSemaphoreType type;

    /// @see VulkanicDevice#createSemaphore
    @ApiStatus.Internal
    public VulkanicSemaphore(VulkanicDevice device, long handle, VulkanicSemaphoreType type) {
        this.device = device;
        this.handle = handle;
        this.type = type;
    }

    @Contract(pure = true)
    public @NotNull VulkanicSemaphoreType type() {
        return type;
    }

    @Contract(mutates = "io")
    public @NotNull VulkanicResult signal(long value) {
        if (type != VulkanicSemaphoreType.TIMELINE) throw new UnsupportedOperationException("Only timeline semaphores can be signaled from the CPU.");
        return device.signalSemaphore(this, value);
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        device.destroySemaphore(this);
    }

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.SEMAPHORE;
    }
}
