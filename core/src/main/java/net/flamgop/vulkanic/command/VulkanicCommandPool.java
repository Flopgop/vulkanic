package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.core.VulkanicQueueFamily;
import net.flamgop.vulkanic.exception.VulkanicResult;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo;
import org.lwjgl.vulkan.VkCommandBufferBeginInfo;

@SuppressWarnings("ClassCanBeRecord")
public class VulkanicCommandPool implements AutoCloseable {

    private final VulkanicDevice device;
    private final long handle;

    /// @see VulkanicDevice#createCommandPool
    @ApiStatus.Internal
    public VulkanicCommandPool(@NotNull VulkanicDevice device, long handle) {
        this.device = device;
        this.handle = handle;
    }

    public @NotNull VulkanicDevice device() {
        return device;
    }

    public int reset(int flags) {
        return device.resetCommandPool(this, flags);
    }

    public @NotNull VulkanicCommandBuffer allocateCommandBuffer(@NotNull VulkanicCommandBufferLevel level) {
        return device.allocateCommandBuffer(this, level);
    }

    public @NotNull VulkanicCommandBuffer[] allocateCommandBuffers(@NotNull VulkanicCommandBufferLevel level, int count) {
        return device.allocateCommandBuffers(this, level, count);
    }

    public void freeCommandBuffer(@NotNull VulkanicCommandBuffer commandBuffer) {
        device.freeCommandBuffer(this, commandBuffer);
    }

    public void freeCommandBuffers(@NotNull VulkanicCommandBuffer... commandBuffers) {
        device.freeCommandBuffers(this, commandBuffers);
    }

    public @NotNull VulkanicResult beginCommandBuffer(@NotNull VulkanicCommandBuffer commandBuffer, @NotNull VkCommandBufferBeginInfo beginInfo) {
        return device.beginCommandBuffer(commandBuffer, beginInfo);
    }

    public @NotNull VulkanicResult resetCommandBuffer(@NotNull VulkanicCommandBuffer commandBuffer, int flags) {
        return device.resetCommandBuffer(commandBuffer, flags);
    }

    public @NotNull VulkanicResult endCommandBuffer(@NotNull VulkanicCommandBuffer commandBuffer) {
        return device.endCommandBuffer(commandBuffer);
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        device.destroyCommandPool(this);
    }
}
