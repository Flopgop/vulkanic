package net.flamgop.vulkanic.sync;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public final class VulkanicQueryPool implements AutoCloseable, VulkanicObject.Opaque {

    private final VulkanicDevice device;
    private final long handle;

    private final int queryCount;

    /// @see VulkanicDevice#createQueryPool
    @ApiStatus.Internal
    public VulkanicQueryPool(@NotNull VulkanicDevice device, long handle, int queryCount) {
        this.device = device;
        this.handle = handle;
        this.queryCount = queryCount;
    }

    public int queryCount() {
        return queryCount;
    }

    public void reset() {
        device.resetQueryPool(this, 0, queryCount);
    }

    @ApiStatus.Internal
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        this.device.destroyQueryPool(this);
    }

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.QUERY_POOL;
    }
}
