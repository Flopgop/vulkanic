package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VulkanicPipelineLayout implements AutoCloseable, VulkanicObject.Opaque {

    private final VulkanicDevice device;
    private final long handle;
    private final long pushConstantSize;

    /// @see VulkanicDevice#createPipelineLayout
    @ApiStatus.Internal
    public VulkanicPipelineLayout(VulkanicDevice device, long handle, long pushConstantSize) {
        this.device = device;
        this.handle = handle;
        this.pushConstantSize = pushConstantSize;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Contract(pure = true)
    public long pushConstantSize() {
        return pushConstantSize;
    }

    @Override
    public void close() {
        device.destroyPipelineLayout(this);
    }

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.PIPELINE_LAYOUT;
    }
}
