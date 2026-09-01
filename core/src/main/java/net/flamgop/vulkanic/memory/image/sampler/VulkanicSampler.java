package net.flamgop.vulkanic.memory.image.sampler;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VulkanicSampler implements AutoCloseable, VulkanicObject.Opaque {

    private final VulkanicDevice device;
    private final VulkanicSamplerCreateInfo createInfo;
    private final long handle;

    /// @see VulkanicDevice#createSampler
    @ApiStatus.Internal
    public VulkanicSampler(@NotNull VulkanicDevice device, @NotNull VulkanicSamplerCreateInfo createInfo, long handle) {
        this.device = device;
        this.createInfo = createInfo;
        this.handle = handle;
    }

    @Contract(pure = true)
    public @NotNull VulkanicSamplerCreateInfo createInfo() {
        return createInfo;
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

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.SAMPLER;
    }
}
