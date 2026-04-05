package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.command.VulkanicCommandBuffer;
import net.flamgop.vulkanic.exception.VulkanicResult;
import net.flamgop.vulkanic.swapchain.VulkanicSwapchain;
import net.flamgop.vulkanic.sync.VulkanicFence;
import net.flamgop.vulkanic.sync.VulkanicSemaphore;
import net.flamgop.vulkanic.sync.VulkanicSemaphoreSubmit;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.*;

import java.util.List;

public record VulkanicQueue(int family, @NotNull VkQueue handle) {

    @ApiStatus.Internal
    public VulkanicQueue {}

    public int bindSparse(@NotNull VkBindSparseInfo bindInfo, @NotNull VulkanicFence fence) {
        return VK11.vkQueueBindSparse(this.handle, bindInfo, fence.handle());
    }

    public int bindSparse(@Nullable VkBindSparseInfo.Buffer bindInfos, @NotNull VulkanicFence fence) {
        return VK11.vkQueueBindSparse(this.handle, bindInfos, fence.handle());
    }

    @SuppressWarnings("resource")
    public int submit(
            @NotNull VulkanicFence fence,
            @NotNull List<VulkanicSemaphoreSubmit> waitSemaphores,
            @NotNull List<VulkanicSemaphoreSubmit> signalSemaphores,
            @NotNull VulkanicCommandBuffer... commandBuffers
    ) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkCommandBufferSubmitInfo.Buffer commandBufferInfos = VkCommandBufferSubmitInfo.calloc(commandBuffers.length, stack);
            for (int i = 0; i < commandBuffers.length; i++) {
                VulkanicCommandBuffer commandBuffer = commandBuffers[i];
                commandBufferInfos.get(i)
                        .sType$Default()
                        .commandBuffer(commandBuffer.handle());
            }

            VkSemaphoreSubmitInfo.Buffer pWaitSemaphores = VkSemaphoreSubmitInfo.calloc(waitSemaphores.size(), stack);
            for (int i = 0; i < waitSemaphores.size(); i++) {
                VulkanicSemaphoreSubmit submit = waitSemaphores.get(i);
                pWaitSemaphores.get(i)
                        .sType$Default()
                        .semaphore(submit.semaphore().handle())
                        .stageMask(submit.stageMask().mask());
            }

            VkSemaphoreSubmitInfo.Buffer pSignalSemaphores = VkSemaphoreSubmitInfo.calloc(signalSemaphores.size(), stack);
            for (int i = 0; i < signalSemaphores.size(); i++) {
                VulkanicSemaphoreSubmit submit = signalSemaphores.get(i);
                pSignalSemaphores.get(i)
                        .sType$Default()
                        .semaphore(submit.semaphore().handle())
                        .stageMask(submit.stageMask().mask());
            }

            VkSubmitInfo2.Buffer submitInfo = VkSubmitInfo2.calloc(1, stack)
                    .sType$Default()
                    .pCommandBufferInfos(commandBufferInfos)
                    .pWaitSemaphoreInfos(pWaitSemaphores)
                    .pSignalSemaphoreInfos(pSignalSemaphores);

            return VK13.vkQueueSubmit2(this.handle, submitInfo, fence.handle());
        }
    }

    public VulkanicResult present(@NotNull VulkanicSwapchain swapchain, int imageIndex, @NotNull VulkanicSemaphore semaphore) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            return VulkanicResult.valueOf(KHRSwapchain.vkQueuePresentKHR(this.handle, VkPresentInfoKHR.calloc(stack)
                    .sType$Default()
                    .pImageIndices(stack.ints(imageIndex))
                    .pWaitSemaphores(stack.longs(semaphore.handle()))
                    .pSwapchains(stack.longs(swapchain.handle()))
                    .swapchainCount(1)));
        }
    }

    public int waitIdle() {
        return VK11.vkQueueWaitIdle(this.handle);
    }

    @Override
    @ApiStatus.Internal
    @Contract(pure = true)
    public @NotNull VkQueue handle() {
        return handle;
    }
}
