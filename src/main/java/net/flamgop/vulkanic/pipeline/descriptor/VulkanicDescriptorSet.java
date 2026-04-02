package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class VulkanicDescriptorSet implements AutoCloseable {
    private final VulkanicDevice device;
    private final long handle;

    @ApiStatus.Internal
    public VulkanicDescriptorSet(@NotNull VulkanicDevice device, long handle) {
        this.device = device;
        this.handle = handle;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        // TODO: figure out a good way to destroy this
    }
}
