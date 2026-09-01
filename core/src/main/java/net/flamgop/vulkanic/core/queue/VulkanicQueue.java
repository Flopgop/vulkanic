package net.flamgop.vulkanic.core.queue;

import net.flamgop.vulkanic.command.VulkanicCommandBuffer;
import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import net.flamgop.vulkanic.exception.VulkanicResult;
import net.flamgop.vulkanic.swapchain.VulkanicSwapchain;
import net.flamgop.vulkanic.sync.VulkanicFence;
import net.flamgop.vulkanic.sync.VulkanicSemaphore;
import net.flamgop.vulkanic.sync.VulkanicSemaphoreSubmit;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.*;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.List;

public record VulkanicQueue(@NotNull VulkanicQueueFamily family, @NotNull VkQueue handle) implements VulkanicObject.Typed<VkQueue> {

    /// @see VulkanicQueueFamily#queue
    @ApiStatus.Internal
    public VulkanicQueue {}

    /// Binds memory allocations to sparse resources on this queue
    /// Waits for all semaphores in each info's wait semaphores list before executing all memory bind operations.
    /// Once all bindings are complete, all signal semaphores are signaled, and finally the fence is signaled.
    @SuppressWarnings("resource")
    public @NotNull VulkanicResult bindSparse(@NotNull List<VulkanicBindSparseInfo> bindInfos, @NotNull VulkanicFence fence) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkBindSparseInfo.Buffer pBindInfos = VkBindSparseInfo.calloc(bindInfos.size(), stack);

            for (int i = 0; i < bindInfos.size(); i++) {
                VulkanicBindSparseInfo info = bindInfos.get(i);
                LongBuffer pWaitSemaphores = stack.callocLong(info.waitSemaphores().size());
                LongBuffer pSignalSemaphores = stack.callocLong(info.signalSemaphores().size());

                VkSparseBufferMemoryBindInfo.Buffer pBufferBinds = VkSparseBufferMemoryBindInfo.calloc(info.bufferBinds().size(), stack);
                for (int j = 0; j < info.bufferBinds().size(); j++) {
                    VulkanicSparseBufferMemoryBindInfo bind = info.bufferBinds().get(j);
                    VkSparseMemoryBind.Buffer pBinds = VkSparseMemoryBind.calloc(bind.binds().size(), stack);
                    for (int k = 0; k < bind.binds().size(); k++)
                        bind.binds().get(k).get(pBinds.get(k));

                    pBufferBinds.get(j)
                            .buffer(bind.buffer().handle())
                            .pBinds(pBinds);
                }

                VkSparseImageOpaqueMemoryBindInfo.Buffer pImageOpaqueBinds = VkSparseImageOpaqueMemoryBindInfo.calloc(info.imageOpaqueBinds().size(), stack);
                for (int j = 0; j < info.imageOpaqueBinds().size(); j++) {
                    VulkanicSparseImageOpaqueMemoryBindInfo bind = info.imageOpaqueBinds().get(j);
                    VkSparseMemoryBind.Buffer pBinds = VkSparseMemoryBind.calloc(bind.binds().size(), stack);
                    for (int k = 0; k < bind.binds().size(); k++)
                        bind.binds().get(k).get(pBinds.get(k));

                    pImageOpaqueBinds.get(j)
                            .image(bind.image().handle())
                            .pBinds(pBinds);
                }

                VkSparseImageMemoryBindInfo.Buffer pImageBinds = VkSparseImageMemoryBindInfo.calloc(info.imageBinds().size(), stack);
                for (int j = 0; j < info.imageBinds().size(); j++) {
                    VulkanicSparseImageMemoryBindInfo bind = info.imageBinds().get(j);
                    VkSparseImageMemoryBind.Buffer pBinds = VkSparseImageMemoryBind.calloc(bind.binds().size(), stack);
                    for (int k = 0; k < bind.binds().size(); k++)
                        bind.binds().get(k).get(pBinds.get(k));

                    pImageBinds.get(j)
                            .image(bind.image().handle())
                            .pBinds(pBinds);
                }

                pBindInfos.get(i)
                        .sType$Default()
                        .pWaitSemaphores(pWaitSemaphores)
                        .pSignalSemaphores(pSignalSemaphores)
                        .pBufferBinds(pBufferBinds)
                        .pImageOpaqueBinds(pImageOpaqueBinds)
                        .pImageBinds(pImageBinds);
            }

            return VulkanicResult.valueOf(VK11.vkQueueBindSparse(this.handle, pBindInfos, fence.handle()));
        }
    }

    /// Waits until all semaphores in the waitSemaphores list are signaled then resets them and executes all command buffers.
    /// Once all command buffers have finished execution, the GPU signals all semaphores in signalSemaphores and finally signals the fence.
    @SuppressWarnings("resource")
    public @NotNull VulkanicResult submit(
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

            return VulkanicResult.valueOf(VK13.vkQueueSubmit2(this.handle, submitInfo, fence.handle()));
        }
    }

    /// Blocks until all semaphores are signaled, then presents each image to each swapchain. Returns a list of results, one for each swapchain.
    public @NotNull List<VulkanicResult> present(
            @NotNull List<VulkanicSemaphore> semaphores,
            @NotNull List<VulkanicSwapchain> swapchains,
            int @NotNull ... images
    ) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pSwapchains = stack.callocLong(swapchains.size());
            LongBuffer pSemaphores = stack.callocLong(semaphores.size());

            for (int i = 0; i < swapchains.size(); i++) {
                pSwapchains.put(i, swapchains.get(i).handle());
            }
            for (int i = 0; i < semaphores.size(); i++) {
                pSemaphores.put(i, semaphores.get(i).handle());
            }

            IntBuffer pResults = stack.callocInt(swapchains.size());

            VkPresentInfoKHR presentInfo = VkPresentInfoKHR.calloc(stack)
                    .sType$Default()
                    .pWaitSemaphores(pSemaphores)
                    .swapchainCount(swapchains.size())
                    .pImageIndices(stack.ints(images))
                    .pSwapchains(pSwapchains)
                    .pResults(pResults);

            KHRSwapchain.vkQueuePresentKHR(this.handle, presentInfo);

            List<VulkanicResult> results = new ArrayList<>();
            for (int i = 0; i < swapchains.size(); i++) {
                results.add(VulkanicResult.valueOf(pResults.get(i)));
            }
            return results;
        }
    }

    /// Waits until this queue is fully idle
    public @NotNull VulkanicResult waitIdle() {
        return VulkanicResult.valueOf(VK11.vkQueueWaitIdle(this.handle));
    }

    @Override
    @ApiStatus.Internal
    @Contract(pure = true)
    public @NotNull VkQueue handle() {
        return handle;
    }

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.QUEUE;
    }
}
