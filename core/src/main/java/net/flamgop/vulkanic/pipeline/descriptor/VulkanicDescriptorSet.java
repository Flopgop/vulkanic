package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VulkanicDescriptorSet implements AutoCloseable, VulkanicObject.Opaque {
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

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.DESCRIPTOR_SET;
    }
}
