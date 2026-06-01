package net.flamgop.vulkanic.swapchain;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.exception.VulkanException;
import net.flamgop.vulkanic.exception.VulkanicResult;
import net.flamgop.vulkanic.memory.VulkanicFormat;
import net.flamgop.vulkanic.memory.image.*;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicSampleCountFlag;
import net.flamgop.vulkanic.sync.VulkanicFence;
import net.flamgop.vulkanic.sync.VulkanicSemaphore;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.*;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkAcquireNextImageInfoKHR;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class VulkanicSwapchain implements AutoCloseable {

    private final VulkanicDevice device;
    private final VulkanicSwapchainCreateInfo createInfo;
    private final long handle;

    private final List<VulkanicImage> images;

    /// @see VulkanicDevice#createSwapchain 
    @ApiStatus.Internal
    public VulkanicSwapchain(@NotNull VulkanicDevice device, @NotNull VulkanicSwapchainCreateInfo createInfo, long handle) {
        this.device = device;
        this.createInfo = createInfo;
        this.handle = handle;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pCount = stack.callocInt(1);
            KHRSwapchain.vkGetSwapchainImagesKHR(this.device.handle(), this.handle, pCount, null);
            LongBuffer pSwapchainImages = stack.callocLong(pCount.get(0));
            KHRSwapchain.vkGetSwapchainImagesKHR(this.device.handle(), this.handle, pCount, pSwapchainImages);
            List<VulkanicImage> images = new ArrayList<>();
            VulkanicImageCreateInfo mockCreateInfo = new VulkanicImageCreateInfo( // vulkanic requires a create info for all images,
                    EnumIntBitset.empty(),
                    VulkanicImageType.TYPE_2D,
                    createInfo.imageFormat(),
                    new Vector3i(createInfo.extent(), 1),
                    1, createInfo.imageArrayLayers(),
                    VulkanicSampleCountFlag.COUNT_1_BIT,
                    VulkanicImageTiling.OPTIMAL,
                    createInfo.imageUsage(),
                    createInfo.imageSharingMode(),
                    VulkanicImageLayout.UNDEFINED,
                    createInfo.queueFamilyIndices()
            );
            for (int i = 0; i < pCount.get(0); i++) {
                images.add(new VulkanicImage(pSwapchainImages.get(i), mockCreateInfo));
            }
            this.images = List.copyOf(images);
        }
    }

    @Contract(pure = true)
    public @NotNull VulkanicSwapchainCreateInfo createInfo() {
        return createInfo;
    }

    public int acquireNextImage(@NotNull Duration timeout, @Nullable VulkanicFence fence, @Nullable VulkanicSemaphore semaphore) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pImage = stack.callocInt(1);
            VulkanicResult ret = VulkanicResult.valueOf(KHRSwapchain.vkAcquireNextImage2KHR(this.device.handle(), VkAcquireNextImageInfoKHR.calloc(stack)
                    .sType$Default()
                    .swapchain(this.handle)
                    .timeout(timeout.toNanos())
                    .deviceMask(1)
                    .fence(fence != null ? fence.handle() : VK10.VK_NULL_HANDLE)
                    .semaphore(semaphore != null ? semaphore.handle() : VK10.VK_NULL_HANDLE), pImage));
            if (!ret.success()) {
                throw new VulkanException(ret);
            }
            return pImage.get(0);
        }
    }

    public @NotNull @Unmodifiable List<VulkanicImage> images() {
        return images;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        device.destroySwapchain(this);
    }
}
