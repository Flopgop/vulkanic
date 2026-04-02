package net.flamgop.vulkanic.sync;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.exception.VulkanicResult;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

/// note: most of the methods in this class are aliases to the relevant device method
public class VulkanicFence implements AutoCloseable {

    private final VulkanicDevice device;
    private final long handle;

    @ApiStatus.Internal
    public VulkanicFence(VulkanicDevice device, long handle) {
        this.device = device;
        this.handle = handle;
    }

    public @NotNull VulkanicResult waitFor(@NotNull Duration timeout) {
        return device.waitForFence(this, timeout);
    }

    public @NotNull VulkanicResult status() {
        return device.fenceStatus(this);
    }

    public @NotNull VulkanicResult reset() {
        return device.resetFence(this);
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        device.destroyFence(this);
    }
}
