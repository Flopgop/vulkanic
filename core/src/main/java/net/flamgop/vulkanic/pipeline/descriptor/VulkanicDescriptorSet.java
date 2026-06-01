package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VulkanicDescriptorSet implements AutoCloseable {
    private final VulkanicDevice device;
    private final VulkanicDescriptorPool pool;
    private final long handle;

    /// @see VulkanicDescriptorPool#allocate 
    @ApiStatus.Internal
    public VulkanicDescriptorSet(@NotNull VulkanicDevice device, @NotNull VulkanicDescriptorPool pool, long handle) {
        this.device = device;
        this.pool = pool;
        this.handle = handle;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        if (pool.createInfo().flags().contains(VulkanicDescriptorPoolCreateFlag.FREE_DESCRIPTOR_SET)) device.freeDescriptorSets(pool, this);
    }
}
