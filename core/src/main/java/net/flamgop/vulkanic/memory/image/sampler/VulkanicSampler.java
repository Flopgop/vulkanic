package net.flamgop.vulkanic.memory.image.sampler;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.memory.image.VulkanicFilter;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicCompareOp;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class VulkanicSampler implements AutoCloseable {

    private final VulkanicDevice device;
    private final long handle;

    /// @see VulkanicDevice#createSampler
    @ApiStatus.Internal
    public VulkanicSampler(@NotNull VulkanicDevice device, long handle) {
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
        device.destroySampler(this);
    }
}
