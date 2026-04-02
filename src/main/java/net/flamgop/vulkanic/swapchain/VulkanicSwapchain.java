package net.flamgop.vulkanic.swapchain;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.exception.VulkanException;
import net.flamgop.vulkanic.exception.VulkanicResult;
import net.flamgop.vulkanic.memory.image.VulkanicImage;
import net.flamgop.vulkanic.memory.image.VulkanicImageAspectFlag;
import net.flamgop.vulkanic.sync.VulkanicFence;
import net.flamgop.vulkanic.sync.VulkanicSemaphore;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.*;
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
    private final long handle;

    private final List<VulkanicImage> images;

    public VulkanicSwapchain(@NotNull VulkanicDevice device, long handle) {
        this.device = device;
        this.handle = handle;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pCount = stack.callocInt(1);
            KHRSwapchain.vkGetSwapchainImagesKHR(this.device.handle(), this.handle, pCount, null);
            LongBuffer pSwapchainImages = stack.callocLong(pCount.get(0));
            KHRSwapchain.vkGetSwapchainImagesKHR(this.device.handle(), this.handle, pCount, pSwapchainImages);
            List<VulkanicImage> images = new ArrayList<>();
            for (int i = 0; i < pCount.get(0); i++) {
                images.add(new VulkanicImage(pSwapchainImages.get(i), EnumIntBitset.of(VulkanicImageAspectFlag.COLOR)));
            }
            this.images = List.copyOf(images);
        }
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
