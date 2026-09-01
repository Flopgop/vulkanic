package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VulkanicDescriptorSetLayout implements AutoCloseable, VulkanicObject.Opaque {
    private final VulkanicDevice device;
    private final long handle;

    /// @see VulkanicDevice#createDescriptorSetLayout
    @ApiStatus.Internal
    public VulkanicDescriptorSetLayout(@NotNull VulkanicDevice device, long handle) {
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
        device.destroyDescriptorSetLayout(this);
    }

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.DESCRIPTOR_SET_LAYOUT;
    }
}
