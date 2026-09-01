package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VulkanicImageView implements AutoCloseable, VulkanicObject.Opaque {

    private final VulkanicDevice device;
    private final VulkanicImage image; // TODO: maybe use this?
    private final VulkanicImageViewCreateInfo createInfo;
    private final long handle;

    /// @see VulkanicDevice#createImageView
    @ApiStatus.Internal
    public VulkanicImageView(@NotNull VulkanicDevice device, @NotNull VulkanicImage image, @NotNull VulkanicImageViewCreateInfo createInfo, long handle) {
        this.device = device;
        this.image = image;
        this.createInfo = createInfo;
        this.handle = handle;
    }

    @Contract(pure = true)
    public @NotNull VulkanicImageViewCreateInfo createInfo() {
        return createInfo;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        device.destroyImageView(this);
    }

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.IMAGE_VIEW;
    }
}
