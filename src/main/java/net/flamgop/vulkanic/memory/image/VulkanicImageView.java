package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class VulkanicImageView implements AutoCloseable {

    private final VulkanicDevice device;
    private final VulkanicImage image; // TODO: maybe use this?
    private final long handle;

    @ApiStatus.Internal
    public VulkanicImageView(@NotNull VulkanicDevice device, @NotNull VulkanicImage image, long handle) {
        this.device = device;
        this.image = image;
        this.handle = handle;
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
}
