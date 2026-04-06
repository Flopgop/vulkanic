package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.lwjgl.vulkan.VkRenderPassCreateInfo;

public class VulkanicRenderPass implements AutoCloseable {

    private final VulkanicDevice device;
    private final long handle;

    /// @see VulkanicDevice#createRenderPass
    @ApiStatus.Internal
    public VulkanicRenderPass(VulkanicDevice device, long handle) {
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
        device.destroyRenderPass(this);
    }
}
