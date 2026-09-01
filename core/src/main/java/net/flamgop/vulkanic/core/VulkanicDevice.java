package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.command.*;
import net.flamgop.vulkanic.core.debug.VulkanicDebugObjectNameInfo;
import net.flamgop.vulkanic.core.feature.VulkanicDeviceFeatures;
import net.flamgop.vulkanic.core.queue.VulkanicQueue;
import net.flamgop.vulkanic.core.queue.VulkanicQueueCreateFlag;
import net.flamgop.vulkanic.core.queue.VulkanicQueueFamily;
import net.flamgop.vulkanic.core.queue.VulkanicQueueInfo;
import net.flamgop.vulkanic.exception.VulkanException;
import net.flamgop.vulkanic.exception.VulkanicResult;
import net.flamgop.vulkanic.memory.*;
import net.flamgop.vulkanic.memory.image.*;
import net.flamgop.vulkanic.memory.image.sampler.*;
import net.flamgop.vulkanic.memory.platform.VulkanicImportInfo;
import net.flamgop.vulkanic.pipeline.*;
import net.flamgop.vulkanic.pipeline.descriptor.*;
import net.flamgop.vulkanic.pipeline.descriptor.heap.*;
import net.flamgop.vulkanic.pipeline.graphics.*;
import net.flamgop.vulkanic.pipeline.graphics.renderpass.*;
import net.flamgop.vulkanic.swapchain.*;
import net.flamgop.vulkanic.sync.*;
import net.flamgop.vulkanic.util.EnumIntBitset;
import net.flamgop.vulkanic.util.VkUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public final class VulkanicDevice implements AutoCloseable, VulkanicObject.Typed<VkDevice> {

    private final VulkanicInstance instance;
    private final VkDevice handle;
    private final VulkanicPhysicalDevice physicalDevice;
    private final VulkanicDeviceFeatures features;

    private final Map<Integer, VulkanicQueueFamily> queueFamilies;

    private final Collection<String> enabledExtensions;
    private final List<String> enabledLayers;

    @ApiStatus.Internal
    public VulkanicDevice(
            @NotNull VulkanicInstance instance,
            @NotNull VkDevice handle,
            @NotNull VulkanicPhysicalDevice physicalDevice,
            @NotNull VulkanicDeviceFeatures features,
            @NotNull List<VulkanicQueueInfo> queueInfos,
            @NotNull Collection<String> extensions,
            @NotNull Collection<String> layers
    ) {
        this.instance = instance;
        this.handle = handle;
        this.physicalDevice = physicalDevice;
        this.features = features;
        this.queueFamilies = new HashMap<>(queueInfos.size());
        for (VulkanicQueueInfo createInfo : queueInfos) {
            queueFamilies.put(createInfo.queueFamilyIndex(), new VulkanicQueueFamily(this, createInfo.queueFamilyIndex(), createInfo.queueCount(), createInfo.createFlags().contains(VulkanicQueueCreateFlag.PROTECTED)));
        }
        this.enabledExtensions = List.copyOf(extensions);
        this.enabledLayers = List.copyOf(layers);
    }

    /// This constructor will fail if any extensions or features requested are not supported by the target physical device.
    ///
    /// This constructor will fail if any layers requested are not present.
    ///
    /// There isn't a widely accepted best practice for selecting a good physical device,
    /// but a common method is assigning each physical device a score based on important characteristics (such as available memory, required features, etc.)
    /// then choosing the best device from the scored list.
    ///
    /// @param physicalDevice A [VulkanicPhysicalDevice] obtained from a [VulkanicInstance] via [VulkanicInstance#enumeratePhysicalDevices]
    /// @param extensions A collection of Vulkan extensions to apply
    /// @param layers A collection of Vulkan Validation Layers to apply
    /// @param queueCreateInfos A list of queue infos to prepopulate this device's queue families
    /// @param features A [VulkanicDeviceFeatures] object configured for the features required of this device
    /// @see VulkanicInstance#enumeratePhysicalDevices
    /// @see VulkanicPhysicalDevice#supportedExtensions
    /// @see VulkanicPhysicalDevice#supportsFeatures
    public VulkanicDevice(
            @NotNull VulkanicInstance instance,
            @NotNull VulkanicPhysicalDevice physicalDevice,
            @NotNull Collection<String> extensions,
            @NotNull Collection<String> layers,
            @NotNull List<VulkanicQueueInfo> queueCreateInfos,
            @NotNull VulkanicDeviceFeatures features
    ) throws VulkanException {
        this.instance = instance;
        this.features = features;
        this.physicalDevice = physicalDevice;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer pDevice = stack.callocPointer(1);

            Set<String> requiredExtensions = new LinkedHashSet<>(features.requiredExtensions());
            requiredExtensions.addAll(extensions);

            this.enabledExtensions = requiredExtensions;
            this.enabledLayers = List.copyOf(layers);

            if (!physicalDevice.supportsFeatures(features)) throw new UnsupportedOperationException("Requested device features are not supported by the selected physical device.");

            PointerBuffer layersBuf = stack.callocPointer(layers.size());
            PointerBuffer extensionsBuf = stack.callocPointer(requiredExtensions.size());
            VkUtil.copyToBuffer(layersBuf, stack, layers);
            VkUtil.copyToBuffer(extensionsBuf, stack, requiredExtensions);
            layersBuf.flip();
            extensionsBuf.flip();

            VkDeviceQueueCreateInfo.Buffer pQueueCreateInfos = VkDeviceQueueCreateInfo.calloc(queueCreateInfos.size(), stack);
            for (int i = 0; i < queueCreateInfos.size(); i++) {
                VulkanicQueueInfo queueInfo = queueCreateInfos.get(i);
                //noinspection resource
                pQueueCreateInfos.get(i)
                        .sType$Default()
                        .queueFamilyIndex(queueInfo.queueFamilyIndex())
                        .pQueuePriorities(stack.floats(queueInfo.queuePriorities()))
                        .flags(queueInfo.createFlags().mask());
            }

            VkDeviceCreateInfo ci = VkDeviceCreateInfo.calloc(stack)
                    .sType$Default()
                    .ppEnabledLayerNames(layersBuf)
                    .ppEnabledExtensionNames(extensionsBuf)
                    .pQueueCreateInfos(pQueueCreateInfos)
                    .pNext(features.pNext());

            VkUtil.check(VK11.vkCreateDevice(physicalDevice.handle(), ci, null, pDevice));
            handle = new VkDevice(pDevice.get(0), physicalDevice.handle(), ci);

            this.queueFamilies = new HashMap<>(queueCreateInfos.size());
            for (VulkanicQueueInfo createInfo : queueCreateInfos) {
                queueFamilies.put(createInfo.queueFamilyIndex(), new VulkanicQueueFamily(this, createInfo.queueFamilyIndex(), createInfo.queueCount(), createInfo.createFlags().contains(VulkanicQueueCreateFlag.PROTECTED)));
            }
        }
    }

    private static void assertSupportsAllExtensions(VulkanicPhysicalDevice physicalDevice, Collection<String> extensions) {
        List<VulkanicExtensionProperties> extensionProperties = physicalDevice.supportedExtensions();
        Set<String> availableExtensions = extensionProperties.stream()
                .map(VulkanicExtensionProperties::name)
                .collect(Collectors.toSet());
        for (String extension : extensions) {
            if (!availableExtensions.contains(extension)) {
                throw new UnsupportedOperationException(String.format("Extension %s is not supported", extension));
            }
        }
    }

    public void setObjectName(@NotNull VulkanicDebugObjectNameInfo info) {
        if (!this.instance.enabledExtensions().contains(EXTDebugUtils.VK_EXT_DEBUG_UTILS_EXTENSION_NAME)) throw new UnsupportedOperationException("VulkanicDevice#setObjectName requires VK_EXT_DEBUG_UTILS extension");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkDebugUtilsObjectNameInfoEXT pInfo = VkDebugUtilsObjectNameInfoEXT.calloc(stack)
                    .sType$Default()
                    .objectType(info.objectType().qualifier())
                    .objectHandle(info.objectHandle())
                    .pObjectName(stack.UTF8(info.objectName()));
            EXTDebugUtils.vkSetDebugUtilsObjectNameEXT(this.handle, pInfo);
        }
    }

    /// This function uses [List#stream()] and an `anyMatch` call to check this, and as such is O(N)
    /// @return Whether this device was configured with a specific extension on creation.
    public boolean supportsExtension(String extension) {
        return enabledExtensions.stream().anyMatch(extension::equals);
    }

    /// @return The [VulkanicPhysicalDevice] this device was created with
    public @NotNull VulkanicPhysicalDevice physicalDevice() {
        return physicalDevice;
    }

    /// @apiNote This structure may not contain all the features the device supports, but will always contain all the features requested as the constructor throws an error if otherwise.
    /// @return The [VulkanicDeviceFeatures] this device was created with
    public @NotNull VulkanicDeviceFeatures features() {
        return features;
    }

    /// @apiNote index refers to the actual queue family index as determined during queue family enumeration (i.e., at device creation time)
    /// @param index the queue family index of the queue family
    /// @return the VulkanicQueueFamily for the queue family index, or null if that index was not passed as a VulkanicQueueInfo to this device's constructor,
    /// this isn't marked as nullable because you can assume that if device creation succeeds, the queue at the specified index does exist.
    public VulkanicQueueFamily queueFamily(int index) {
        return queueFamilies.get(index);
    }

    /// __Do not use this.__
    @ApiStatus.Internal
    public @NotNull VulkanicQueue queue(@NotNull VulkanicQueueFamily queueFamily, int queueIndex) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer pQueue = stack.callocPointer(1);
            VK11.vkGetDeviceQueue(this.handle, queueFamily.index(), queueIndex, pQueue);
            return new VulkanicQueue(queueFamily, new VkQueue(pQueue.get(0), this.handle));
        }
    }

    /// Creates a fence. Fences are generally used as a one-way signal flag from the GPU to the CPU. <p/>
    /// If you plan on waiting on this fence before you do any work, consider creating it with [VulkanicFenceCreateFlag#SIGNALED] so your wait finishes immediately.
    /// @param createFlags an [EnumIntBitset<VulkanicFenceCreateFlag>] of flags for this fence.
    /// @return A fence, assuming the creation flags were valid and no driver error occurred.
    /// @see VulkanicFence
    /// @see waitForFence
    /// @see resetFence
    /// @see fenceStatus
    /// @see destroyFence
    public @NotNull VulkanicFence createFence(EnumIntBitset<VulkanicFenceCreateFlag> createFlags) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkFenceCreateInfo createInfo = VkFenceCreateInfo.calloc(stack)
                    .flags(createFlags.mask())
                    .sType$Default();

            LongBuffer pFence = stack.callocLong(1);
            VkUtil.check(VK11.vkCreateFence(this.handle, createInfo, null, pFence));
            return new VulkanicFence(this, pFence.get(0));
        }
    }

    /// Destroys a given fence
    /// @see VulkanicFence
    /// @see createFence
    public void destroyFence(@NotNull VulkanicFence fence) {
        VK11.vkDestroyFence(this.handle, fence.handle(), null);
    }

    /// Resets a given fence, changing its status from SIGNALED to UNSIGNALED. This will not fail if the fence is already unsignaled.<p/>
    /// Use [resetFences] to reset multiple fences at once.
    /// @see VulkanicFence
    /// @see resetFences
    /// @see fenceStatus
    public @NotNull VulkanicResult resetFence(@NotNull VulkanicFence fence) {
        return VulkanicResult.valueOf(VK11.vkResetFences(this.handle, fence.handle()));
    }

    /// Resets several fences, changing their statuses from SIGNALED to UNSIGNALED. This will not fail if any of the fences are unsignaled.<p/>
    /// Use [resetFence] to reset a single fence, this method does a bit of extra work to reset multiple.
    /// @see VulkanicFence
    /// @see resetFence
    /// @see fenceStatus
    public @NotNull VulkanicResult resetFences(@NotNull VulkanicFence... fences) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pFences = stack.callocLong(fences.length);
            for (VulkanicFence fence : fences) {
                pFences.put(fence.handle());
            }
            pFences.flip();
            return VulkanicResult.valueOf(VK11.vkResetFences(this.handle, pFences));
        }
    }

    /// Queries a fences status.
    /// @return [VulkanicResult#SUCCESS] if the fence is signaled, [VulkanicResult#NOT_READY] if the fence is not signaled, [VulkanicResult#ERROR_DEVICE_LOST] if the device was lost.
    /// @see VulkanicFence
    public @NotNull VulkanicResult fenceStatus(@NotNull VulkanicFence fence) {
        return VulkanicResult.valueOf(VK11.vkGetFenceStatus(this.handle, fence.handle()));
    }


    /// Synchronously waits for a fence, this blocks until the fence is complete, or `timeout` expires.
    /// @return [VulkanicResult#TIMEOUT] if `timeout` is zero, or if the fence is not signaled before `timeout` expires, otherwise [VulkanicResult#SUCCESS].
    public @NotNull VulkanicResult waitForFence(@NotNull VulkanicFence fence, @NotNull Duration timeout) {
        return VulkanicResult.valueOf(VK11.vkWaitForFences(this.handle, fence.handle(), true, timeout.toNanos()));
    }

    /// Synchronously waits for several fences. <p/>
    /// If `waitAll` is true, this will block until all fences are complete or until `timeout` expires.
    /// If `waitAll` is false, this will block until any fence is complete or until `timeout` expires.
    /// @return [VulkanicResult#TIMEOUT] if `timeout` is zero, or if the fence is not signaled before `timeout` expires, otherwise [VulkanicResult#SUCCESS].
    public @NotNull VulkanicResult waitForFences(@NotNull Duration timeout, boolean waitAll, @NotNull VulkanicFence... fences) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pFences = stack.callocLong(fences.length);
            for (VulkanicFence fence : fences) {
                pFences.put(fence.handle());
            }
            pFences.flip();
            return VulkanicResult.valueOf(VK11.vkWaitForFences(this.handle, pFences, waitAll, timeout.toNanos()));
        }
    }

    /// Creates a semaphore. A semaphore is generally used as a signal passed strictly within the GPU.
    /// These are used for synchronization within the GPU. [VulkanicSemaphoreType#BINARY] semaphores automatically reset when consumed.
    /// [VulkanicSemaphoreType#TIMELINE] semaphores are incremented whenever the GPU executes a signal operation or whenever [#signalSemaphore] is called.
    /// Additionally, timeline semaphores may only ever increase; they cannot be reset.
    /// @param type semaphore type, either [VulkanicSemaphoreType#BINARY] or [VulkanicSemaphoreType#TIMELINE]
    /// @param initialValue only makes sense in version 1.2 and for [VulkanicSemaphoreType#TIMELINE], otherwise must be 0.
    /// @see VulkanicSemaphore
    /// @see signalSemaphore
    /// @see destroySemaphore
    public @NotNull VulkanicSemaphore createSemaphore(@NotNull VulkanicSemaphoreType type, long initialValue) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkSemaphoreCreateInfo createInfo = VkSemaphoreCreateInfo.calloc(stack)
                    .sType$Default();

            if (this.instance.applicationInfo().apiVersion().version() >= ApiVersion.VULKAN_1_2.version() || this.enabledExtensions.contains(KHRTimelineSemaphore.VK_KHR_TIMELINE_SEMAPHORE_EXTENSION_NAME)) {
                createInfo.pNext(VkSemaphoreTypeCreateInfo.calloc(stack)
                        .semaphoreType(type.qualifier())
                        .initialValue(initialValue)
                        .sType$Default());
            }

            LongBuffer pSemaphore = stack.callocLong(1);
            VkUtil.check(VK11.vkCreateSemaphore(this.handle, createInfo, null, pSemaphore));
            return new VulkanicSemaphore(this, pSemaphore.get(0), type);
        }
    }

    /// Signals (by incrementing) a semaphore with a certain value. This is only valid for timeline semaphores.
    /// This requires Vulkan API 1.2 or VK_KHR_TIMELINE_SEMAPHORE
    /// @see VulkanicSemaphore
    public @NotNull VulkanicResult signalSemaphore(@NotNull VulkanicSemaphore semaphore, long value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkSemaphoreSignalInfo signalInfo = VkSemaphoreSignalInfo.calloc(stack)
                    .sType$Default()
                    .semaphore(semaphore.handle())
                    .value(value);

            return VulkanicResult.valueOf(VK12.vkSignalSemaphore(this.handle, signalInfo));
        }
    }

    /// Destroys a semaphore
    /// @see VulkanicSemaphore
    /// @see createSemaphore
    public void destroySemaphore(@NotNull VulkanicSemaphore semaphore) {
        VK11.vkDestroySemaphore(this.handle, semaphore.handle(), null);
    }

    /// Creates a command pool for a given queue family. This object allows you to create [VulkanicCommandBuffer]s, and can only be executed on queues of the given family.
    /// @param flags see [VulkanicCommandPoolCreateFlag]
    /// @param queueFamily the [VulkanicQueueFamily] this command pool belongs to. If this pool is protected, the queue family must also be protected.
    /// @see VulkanicCommandPool
    public @NotNull VulkanicCommandPool createCommandPool(
            EnumIntBitset<VulkanicCommandPoolCreateFlag> flags,
            VulkanicQueueFamily queueFamily
    ) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkCommandPoolCreateInfo createInfo =  VkCommandPoolCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(flags.mask())
                    .queueFamilyIndex(queueFamily.index());
            LongBuffer pCommandPool = stack.callocLong(1);
            VkUtil.check(VK11.vkCreateCommandPool(this.handle, createInfo, null, pCommandPool));
            return new VulkanicCommandPool(this, pCommandPool.get(0), flags.contains(VulkanicCommandPoolCreateFlag.PROTECTED));
        }
    }

    /// Destroys a command pool
    /// @see VulkanicCommandPool
    /// @see createCommandPool
    public void destroyCommandPool(@NotNull VulkanicCommandPool commandPool) {
        VK11.vkDestroyCommandPool(this.handle, commandPool.handle(), null);
    }

    /// Resets a given command pool
    /// @see VulkanicCommandPool
    public @NotNull VulkanicResult resetCommandPool(@NotNull VulkanicCommandPool commandPool, @NotNull EnumIntBitset<VulkanicCommandPoolResetFlag> flags) {
        return VulkanicResult.valueOf(VK11.vkResetCommandPool(this.handle, commandPool.handle(), flags.mask()));
    }

    /// Allocates a command buffer from a pool. This will inherit some properties from the pool, such as the protected bit.
    ///
    /// Secondary command buffers may be used in parallelism workloads, i.e., two threads may simultaneously record different geometry into different (secondary) command buffers, which then would be submitted to [VulkanicCommandBuffer#executeCommands] on a primary command buffer to be more efficient with command recording.
    ///
    /// Secondary command buffers may also be used to record static geometry and then be stored to be re-executed in multiple frames, to avoid re-recording the same draw calls every frame.
    /// @param level [VulkanicCommandBufferLevel#PRIMARY] if this is intended to be executed on a queue, [VulkanicCommandBufferLevel#SECONDARY] if this is intended to be executed only within other command buffers.
    /// @see VulkanicCommandBuffer
    public @NotNull VulkanicCommandBuffer allocateCommandBuffer(@NotNull VulkanicCommandPool pool, @NotNull VulkanicCommandBufferLevel level) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkCommandBufferAllocateInfo pAllocateInfo = VkCommandBufferAllocateInfo.calloc(stack)
                    .sType$Default()
                    .commandPool(pool.handle())
                    .level(level.qualifier())
                    .commandBufferCount(1);
            PointerBuffer pCommandBuffers = stack.callocPointer(1);
            VkUtil.check(VK11.vkAllocateCommandBuffers(this.handle, pAllocateInfo, pCommandBuffers));
            return new VulkanicCommandBuffer(pool, new VkCommandBuffer(pCommandBuffers.get(0), this.handle), level);
        }
    }

    /// Allocates multiple command buffers. These will inherit some properties from the pool, such as the protected bit.
    ///
    /// Secondary command buffers may be used in parallelism workloads, i.e., two threads may simultaneously record different geometry into different (secondary) command buffers, which then would be submitted to [VulkanicCommandBuffer#executeCommands] on a primary command buffer to be more efficient with command recording.
    ///
    /// Secondary command buffers may also be used to record static geometry and then be stored to be re-executed in multiple frames, to avoid re-recording the same draw calls every frame.
    /// @param level [VulkanicCommandBufferLevel#PRIMARY] if this is intended to be executed on a queue, [VulkanicCommandBufferLevel#SECONDARY] if this is intended to be executed only within other command buffers.
    /// @see VulkanicCommandBuffer
    public @NotNull VulkanicCommandBuffer[] allocateCommandBuffers(@NotNull VulkanicCommandPool pool, @NotNull VulkanicCommandBufferLevel level, int count) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkCommandBufferAllocateInfo pAllocateInfo = VkCommandBufferAllocateInfo.calloc(stack)
                    .sType$Default()
                    .commandPool(pool.handle())
                    .level(level.qualifier())
                    .commandBufferCount(count);
            PointerBuffer pCommandBuffers = stack.callocPointer(count);
            VkUtil.check(VK11.vkAllocateCommandBuffers(this.handle, pAllocateInfo, pCommandBuffers));
            VulkanicCommandBuffer[] commandBuffers = new VulkanicCommandBuffer[count];
            for (int i = 0; i < count; i++) commandBuffers[i] = new VulkanicCommandBuffer(pool, new VkCommandBuffer(pCommandBuffers.get(i), this.handle), level);
            return commandBuffers;
        }
    }

    /// Free a command buffer.
    /// @see VulkanicCommandBuffer
    public void freeCommandBuffer(@NotNull VulkanicCommandPool pool, @NotNull VulkanicCommandBuffer commandBuffer) {
        VK11.vkFreeCommandBuffers(this.handle, pool.handle(), commandBuffer.handle());
    }

    /// Free multiple command buffers.
    /// @see VulkanicCommandBuffer
    public void freeCommandBuffers(@NotNull VulkanicCommandPool pool, @NotNull VulkanicCommandBuffer... commandBuffers) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer pCommandBuffers = stack.callocPointer(commandBuffers.length);
            for (VulkanicCommandBuffer commandBuffer : commandBuffers) {
                pCommandBuffers.put(commandBuffer.handle());
            }
            pCommandBuffers.flip();
            VK11.vkFreeCommandBuffers(this.handle, pool.handle(), pCommandBuffers);
        }
    }

    /// Begins a command buffer.
    /// @apiNote VulkanicCommandBuffers do not track whether they have begun or not, to preserve java-side immutability guarantees
    /// @param beginInfo see [VulkanicCommandBufferBeginInfo]
    /// @see VulkanicCommandBuffer
    @SuppressWarnings("resource")
    public @NotNull VulkanicResult beginCommandBuffer(@NotNull VulkanicCommandBuffer commandBuffer, @NotNull VulkanicCommandBufferBeginInfo beginInfo) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkCommandBufferInheritanceInfo pInheritanceInfo;
            if (beginInfo.inheritanceInfo() != null) {
                pInheritanceInfo = VkCommandBufferInheritanceInfo.calloc(stack)
                        .sType$Default()
                        .renderPass(beginInfo.inheritanceInfo().renderPass().handle())
                        .subpass(beginInfo.inheritanceInfo().subpass())
                        .framebuffer(beginInfo.inheritanceInfo().framebuffer() != null ? beginInfo.inheritanceInfo().framebuffer().handle() : VK10.VK_NULL_HANDLE)
                        .occlusionQueryEnable(beginInfo.inheritanceInfo().occlusionQueryEnable())
                        .queryFlags(beginInfo.inheritanceInfo().queryFlags().mask())
                        .pipelineStatistics(beginInfo.inheritanceInfo().pipelineStatistics().mask());
            } else pInheritanceInfo = null;
            VkCommandBufferBeginInfo pBeginInfo = VkCommandBufferBeginInfo.calloc(stack)
                    .sType$Default()
                    .flags(beginInfo.flags().mask())
                    .pInheritanceInfo(pInheritanceInfo);
            return VulkanicResult.valueOf(VK11.vkBeginCommandBuffer(commandBuffer.handle(), pBeginInfo));
        }
    }

    /// Ends a command buffer.
    /// @see VulkanicCommandBuffer
    public @NotNull VulkanicResult endCommandBuffer(@NotNull VulkanicCommandBuffer commandBuffer) {
        return VulkanicResult.valueOf(VK11.vkEndCommandBuffer(commandBuffer.handle()));
    }

    /// Resets a command buffer.
    /// @apiNote This is only valid on command buffers created from a pool with the RESET_COMMAND_BUFFER flag.
    /// @param flags see [VulkanicCommandBufferResetFlag]
    /// @see VulkanicCommandBuffer
    public @NotNull VulkanicResult resetCommandBuffer(@NotNull VulkanicCommandBuffer commandBuffer, @NotNull EnumIntBitset<VulkanicCommandBufferResetFlag> flags) {
        return VulkanicResult.valueOf(VK11.vkResetCommandBuffer(commandBuffer.handle(), flags.mask()));
    }

    /// Creates a view for an image. This is just a way the GPU knows how to read an image.
    /// @see VulkanicImageView
    public @NotNull VulkanicImageView createImageView(@NotNull VulkanicImage image, @NotNull VulkanicImageViewCreateInfo createInfo) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pImageView = stack.callocLong(1);
            VkImageViewCreateInfo pCreateInfo = VkImageViewCreateInfo.calloc(stack)
                    .sType$Default()
                    .image(image.handle())
                    .viewType(createInfo.viewType().qualifier())
                    .format(createInfo.format().qualifier())
                    .components(VkComponentMapping.calloc(stack)
                            .r(createInfo.componentMapping().r().qualifier())
                            .g(createInfo.componentMapping().g().qualifier())
                            .b(createInfo.componentMapping().b().qualifier())
                            .a(createInfo.componentMapping().a().qualifier()))
                    .subresourceRange(VkImageSubresourceRange.calloc(stack)
                            .aspectMask(createInfo.subresourceRange().aspectMask().mask())
                            .baseMipLevel(createInfo.subresourceRange().baseMipLevel())
                            .levelCount(createInfo.subresourceRange().levelCount())
                            .baseArrayLayer(createInfo.subresourceRange().baseArrayLayer())
                            .layerCount(createInfo.subresourceRange().layerCount()));
            VkUtil.check(VK11.vkCreateImageView(this.handle, pCreateInfo, null, pImageView));
            return new VulkanicImageView(this, image, createInfo, pImageView.get(0));
        }
    }

    /// Destroys an image view.
    /// @see VulkanicImageView
    /// @see createImageView
    public void destroyImageView(@NotNull VulkanicImageView imageView) {
        VK11.vkDestroyImageView(this.handle, imageView.handle(), null);
    }

    /// Creates a shader module from a byte array of SPIR-V code, compiled with your favorite shader compiler.
    /// @see VulkanicShaderModule
    public @NotNull VulkanicShaderModule createShaderModule(byte @NotNull [] code) throws VulkanException {
        ByteBuffer pCode = MemoryUtil.memAlloc(code.length);
        try {
            pCode.put(code).flip();
            return createShaderModule(pCode);
        } finally {
            MemoryUtil.memFree(pCode);
        }
    }

    /// Creates a shader module from a ByteBuffer of SPIR-V code, compiled with your favorite shader compiler.
    /// @see VulkanicShaderModule
    public @NotNull VulkanicShaderModule createShaderModule(@NotNull ByteBuffer pCode) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pShaderModule = stack.callocLong(1);
            VkUtil.check(VK10.vkCreateShaderModule(handle, VkShaderModuleCreateInfo.calloc(stack)
                    .sType$Default()
                    .pCode(pCode), null, pShaderModule));
            return new VulkanicShaderModule(this, pShaderModule.get(0));
        }
    }

    /// Destroys a shader module.
    /// @see VulkanicShaderModule
    public void destroyShaderModule(@NotNull VulkanicShaderModule module) {
        VK10.vkDestroyShaderModule(handle, module.handle(), null);
    }

    /// Creates a pipeline layout. A pipeline layout defines the general inputs of a given pipeline. Multiple pipelines may share the same layout.
    /// Note: Push constants have a maximum size, see [VulkanicPhysicalDevice#properties()] to find said limit.
    /// @see VulkanicPipelineLayout
    @SuppressWarnings("resource")
    public @NotNull VulkanicPipelineLayout createPipelineLayout(@NotNull EnumIntBitset<VulkanicPipelineLayoutCreateFlag> createFlags, @NotNull List<@NotNull VulkanicDescriptorSetLayout> setLayouts, @NotNull List<@NotNull VulkanicPushConstantRange> pushConstantRanges) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkPushConstantRange.Buffer pPushConstantRanges = VkPushConstantRange.calloc(pushConstantRanges.size(), stack);
            for (int i = 0; i < pushConstantRanges.size(); i++) {
                VulkanicPushConstantRange pushConstantRange = pushConstantRanges.get(i);
                pPushConstantRanges.get(i)
                        .stageFlags(pushConstantRange.stageFlags().mask())
                        .offset(pushConstantRange.offset())
                        .size((int) pushConstantRange.size().bytes()); // note: push constants can't actually usually be larger than like 512 bytes
            }

            LongBuffer pSetLayouts = stack.callocLong(setLayouts.size());
            for (int i = 0; i < setLayouts.size(); i++) {
                pSetLayouts.put(i, setLayouts.get(i).handle());
            }

            VkPipelineLayoutCreateInfo createInfo = VkPipelineLayoutCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(createFlags.mask())
                    .pPushConstantRanges(pPushConstantRanges)
                    .pSetLayouts(pSetLayouts)
                    .setLayoutCount(setLayouts.size());

            LongBuffer pPipelineLayout = stack.callocLong(1);
            VkUtil.check(VK10.vkCreatePipelineLayout(handle, createInfo, null, pPipelineLayout));
            return new VulkanicPipelineLayout(this, pPipelineLayout.get(0), pPushConstantRanges.capacity() > 0 ? pPushConstantRanges.get(0).size() : 0);
        }
    }

    /// Destroys a pipeline layout
    /// @see VulkanicPipelineLayout
    /// @see createPipelineLayout
    public void destroyPipelineLayout(@NotNull VulkanicPipelineLayout layout) {
        VK10.vkDestroyPipelineLayout(handle, layout.handle(), null);
    }

    /// Creates a descriptor set layout. This is necessary for determining the shape of each descriptor pool.
    /// @see VulkanicDescriptorSetLayout
    @SuppressWarnings("resource")
    public @NotNull VulkanicDescriptorSetLayout createDescriptorSetLayout(@NotNull EnumIntBitset<VulkanicDescriptorSetLayoutCreateFlag> flags, @NotNull List<@NotNull VulkanicDescriptorSetLayoutBinding> bindings) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkDescriptorSetLayoutBinding.Buffer pBindings = VkDescriptorSetLayoutBinding.calloc(bindings.size(), stack);
            for (int i = 0; i < bindings.size(); i++) {
                VulkanicDescriptorSetLayoutBinding binding = bindings.get(i);
                LongBuffer pImmutableSamplers = stack.callocLong(binding.immutableSamplers().size());
                for (int j = 0; j <  binding.immutableSamplers().size(); j++) {
                    pImmutableSamplers.put(j, binding.immutableSamplers().get(j).handle());
                }

                pBindings.get(i)
                        .descriptorCount(binding.descriptorCount())
                        .binding(binding.binding())
                        .descriptorType(binding.descriptorType().qualifier())
                        .stageFlags(binding.stageFlags().mask())
                        .pImmutableSamplers(binding.immutableSamplers().isEmpty() ? null : pImmutableSamplers);
            }

            VkDescriptorSetLayoutCreateInfo createInfo = VkDescriptorSetLayoutCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(flags.mask())
                    .pBindings(pBindings);

            LongBuffer pSetLayout = stack.callocLong(1);
            VkUtil.check(VK10.vkCreateDescriptorSetLayout(handle, createInfo, null, pSetLayout));
            return new VulkanicDescriptorSetLayout(this, pSetLayout.get(0));
        }
    }

    /// Destroys a descriptor set layout
    /// @see VulkanicDescriptorSetLayout
    /// @see createDescriptorSetLayout
    public void destroyDescriptorSetLayout(@NotNull VulkanicDescriptorSetLayout descriptorSetLayout) {
        VK10.vkDestroyDescriptorSetLayout(handle, descriptorSetLayout.handle(), null);
    }

    /// Executes a list of descriptor set updates. This can involve writing to or copying between several descriptor sets.
    /// Ideally, you should batch all of your writes and copies together and execute them all at once
    @SuppressWarnings("resource")
    public void updateDescriptorSet(@NotNull List<@NotNull VulkanicUpdateDescriptorSet> updates) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            int writeCount = 0;
            int copyCount = 0;
            for (VulkanicUpdateDescriptorSet update : updates) {
                switch (update) {
                    case VulkanicWriteDescriptorSet _ -> writeCount += 1;
                    case VulkanicCopyDescriptorSet _ -> copyCount += 1;
                }
            }

            VkWriteDescriptorSet.Buffer pWrites = VkWriteDescriptorSet.calloc(writeCount, stack);
            VkCopyDescriptorSet.Buffer pCopies = VkCopyDescriptorSet.calloc(copyCount, stack);
            writeCount = 0; copyCount = 0;
            for (VulkanicUpdateDescriptorSet update : updates) {
                switch (update) {
                    case VulkanicWriteDescriptorSet write -> {
                        VkDescriptorImageInfo.Buffer pImageInfo;
                        if (write.imageInfos() != null) {
                            pImageInfo = VkDescriptorImageInfo.calloc(write.imageInfos().size(), stack);
                            for (int i = 0; i < write.imageInfos().size(); i++) {
                                VulkanicDescriptorImageInfo imageInfo = write.imageInfos().get(i);
                                pImageInfo.get(i)
                                        .sampler(imageInfo.sampler() != null ? imageInfo.sampler().handle() : 0)
                                        .imageView(imageInfo.imageView() != null ? imageInfo.imageView().handle() : 0)
                                        .imageLayout(imageInfo.imageLayout().qualifier());
                            }
                        } else pImageInfo = null;
                        VkDescriptorBufferInfo.Buffer pBufferInfo;
                        if (write.bufferInfos() != null) {
                            pBufferInfo = VkDescriptorBufferInfo.calloc(write.bufferInfos().size(), stack);
                            for (int i = 0; i < write.bufferInfos().size(); i++) {
                                VulkanicDescriptorBufferInfo bufferInfo = write.bufferInfos().get(i);
                                pBufferInfo.get(i)
                                        .buffer(bufferInfo.buffer().handle())
                                        .offset(bufferInfo.offset())
                                        .range(bufferInfo.range());
                            }
                        } else pBufferInfo = null;
                        pWrites.get(writeCount)
                                .sType$Default()
                                .dstSet(write.dstSet().handle())
                                .dstBinding(write.dstBinding())
                                .dstArrayElement(write.dstArrayElement())
                                .descriptorCount(write.descriptorCount())
                                .descriptorType(write.descriptorType().qualifier())
                                .pImageInfo(pImageInfo)
                                .pBufferInfo(pBufferInfo)
                                .pTexelBufferView(write.texelBufferView() != null ? stack.longs(write.texelBufferView()) : null);
                        writeCount += 1;
                    }
                    case VulkanicCopyDescriptorSet copy -> {
                        pCopies.get(copyCount)
                                .sType$Default()
                                .srcSet(copy.srcSet().handle())
                                .srcBinding(copy.srcBinding())
                                .srcArrayElement(copy.srcArrayElement())
                                .dstSet(copy.dstSet().handle())
                                .dstBinding(copy.dstBinding())
                                .dstArrayElement(copy.dstArrayElement())
                                .descriptorCount(copy.descriptorCount());
                        copyCount += 1;
                    }
                }
            }

            VK10.vkUpdateDescriptorSets(this.handle, pWrites, pCopies);
        }
    }

    /// Creates a descriptor pool.
    /// A descriptor pool is where descriptor sets are allocated from, and dictates the maximum amount of resources allowed to be dedicated to those sets.
    /// Multiple unique sets (with unique layouts) can be allocated from one descriptor pool, provided the descriptor pool has enough space for them.
    /// @see VulkanicDescriptorPool
    @SuppressWarnings("resource")
    public @NotNull VulkanicDescriptorPool createDescriptorPool(@NotNull VulkanicDescriptorPoolCreateInfo createInfo) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkDescriptorPoolSize.Buffer pPoolSizes = VkDescriptorPoolSize.calloc(createInfo.poolSizes().size(), stack);
            for (int i = 0; i < createInfo.poolSizes().size(); i++) {
                VulkanicDescriptorPoolSize poolSize = createInfo.poolSizes().get(i);
                pPoolSizes.get(i)
                        .type(poolSize.type().qualifier())
                        .descriptorCount(poolSize.descriptorCount());
            }

            VkDescriptorPoolCreateInfo pCreateInfo = VkDescriptorPoolCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(createInfo.flags().mask())
                    .maxSets(createInfo.maxSets())
                    .pPoolSizes(pPoolSizes);
            LongBuffer pDescriptorPool = stack.callocLong(1);
            VkUtil.check(VK10.vkCreateDescriptorPool(handle, pCreateInfo, null, pDescriptorPool));
            return new VulkanicDescriptorPool(this, createInfo, pDescriptorPool.get(0));
        }
    }

    /// Destroys a descriptor pool and frees all sets allocated from it.
    /// @see VulkanicDescriptorPool
    /// @see createDescriptorPool
    public void destroyDescriptorPool(@NotNull VulkanicDescriptorPool descriptorPool) {
        VK10.vkDestroyDescriptorPool(handle, descriptorPool.handle(), null);
    }

    /// Resets a descriptor pool and frees all sets allocated from it.
    /// @see VulkanicDescriptorPool
    public void resetDescriptorPool(@NotNull VulkanicDescriptorPool pool, @NotNull EnumIntBitset<VulkanicDescriptorPoolResetFlag> flags) throws VulkanException {
        VkUtil.check(VK10.vkResetDescriptorPool(this.handle, pool.handle(), flags.mask()));
    }

    /// Allocates descriptor sets from a descriptor pool.
    /// Prefer [VulkanicDescriptorPool#allocate]
    /// @see VulkanicDescriptorPool
    public @NotNull VulkanicDescriptorSet[] allocateDescriptorSets(@NotNull VulkanicDescriptorPool pool, @NotNull List<@NotNull VulkanicDescriptorSetLayout> setLayouts) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pSetLayouts = stack.callocLong(setLayouts.size());
            for (int i = 0; i < setLayouts.size(); i++) {
                pSetLayouts.put(i, setLayouts.get(i).handle());
            }
            VkDescriptorSetAllocateInfo allocateInfo = VkDescriptorSetAllocateInfo.calloc(stack)
                    .sType$Default()
                    .descriptorPool(pool.handle())
                    .pSetLayouts(pSetLayouts);

            LongBuffer pDescriptorSet = stack.callocLong(setLayouts.size());
            VkUtil.check(VK10.vkAllocateDescriptorSets(handle, allocateInfo, pDescriptorSet));

            VulkanicDescriptorSet[] descriptorSets = new VulkanicDescriptorSet[setLayouts.size()];
            for (int i = 0; i < setLayouts.size(); i++) {
                descriptorSets[i] = new VulkanicDescriptorSet(this, pool, pDescriptorSet.get(i));
            }
            return descriptorSets;
        }
    }

    /// Frees descriptor sets. Descriptor pool must be created with the FREE_DESCRIPTOR_SET flag.
    /// Prefer [VulkanicDescriptorPool#free]
    /// @see VulkanicDescriptorPool
    public void freeDescriptorSets(@NotNull VulkanicDescriptorPool pool, @NotNull VulkanicDescriptorSet... descriptorSets) {
        if (!pool.createInfo().flags().contains(VulkanicDescriptorPoolCreateFlag.FREE_DESCRIPTOR_SET)) throw new IllegalStateException("Freeing a descriptor set on a pool requires the pool to have been created with the free descriptor set flag.");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pDescriptorSets = stack.callocLong(descriptorSets.length);
            for (VulkanicDescriptorSet descriptorSet : descriptorSets) {
                pDescriptorSets.put(descriptorSet.handle());
            }
            pDescriptorSets.flip();
            VK10.vkFreeDescriptorSets(this.handle, pool.handle(), pDescriptorSets);
        }
    }

    /// Creates a framebuffer. Framebuffers are write targets for the old Vulkan rendering style (i.e., using [VulkanicRenderPass]).
    /// Prefer Vulkan 1.2 dynamic rendering, or VK_KHR_dynamic_rendering
    /// @see VulkanicFramebuffer
    @SuppressWarnings("resource")
    public @NotNull VulkanicFramebuffer createFramebuffer(@NotNull VulkanicFramebufferCreateInfo createInfo) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            List<VulkanicImageView> attachments = createInfo.attachments();
            LongBuffer pAttachments;
            if (attachments != null) {
                pAttachments = stack.callocLong(attachments.size());
                for (int i = 0; i < attachments.size(); i++) {
                    pAttachments.put(i, attachments.get(i).handle());
                }
            } else pAttachments = null;

            VkFramebufferCreateInfo pCreateInfo = VkFramebufferCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(createInfo.flags().mask())
                    .renderPass(createInfo.renderPass().handle())
                    .attachmentCount(attachments != null ? attachments.size() : 0)
                    .pAttachments(pAttachments)
                    .width(createInfo.width()).height(createInfo.height()).layers(createInfo.layers());
            LongBuffer pFramebuffer = stack.callocLong(1);
            VkUtil.check(VK10.vkCreateFramebuffer(handle, pCreateInfo, null, pFramebuffer));
            return new VulkanicFramebuffer(this, pFramebuffer.get(0));
        }
    }

    /// Destroys a framebuffer.
    /// @see VulkanicFramebuffer
    public void destroyFramebuffer(@NotNull VulkanicFramebuffer framebuffer) {
        VK10.vkDestroyFramebuffer(handle, framebuffer.handle(), null);
    }

    /// Creates a render pass. These define rendering information like targets, subpasses, and dependencies. This is outdated and should only be used on legacy Vulkan (1.0)
    /// Prefer Vulkan 1.2 dynamic rendering, or VK_KHR_dynamic_rendering
    /// @see VulkanicRenderPass
    @SuppressWarnings("resource")
    public @NotNull VulkanicRenderPass createRenderPass(@NotNull VulkanicRenderPassCreateInfo createInfo) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkAttachmentDescription.Buffer pAttachments = VkAttachmentDescription.calloc(createInfo.attachments().size(), stack);
            for (int i = 0; i < createInfo.attachments().size(); i++) {
                VulkanicAttachmentDescription attachment = createInfo.attachments().get(i);
                pAttachments.get(i)
                        .flags(attachment.flags().mask())
                        .format(attachment.format().qualifier())
                        .samples(attachment.samples().flag())
                        .loadOp(attachment.loadOp().qualifier())
                        .storeOp(attachment.storeOp().qualifier())
                        .stencilLoadOp(attachment.stencilLoadOp().qualifier())
                        .stencilStoreOp(attachment.stencilStoreOp().qualifier())
                        .initialLayout(attachment.initialLayout().qualifier())
                        .finalLayout(attachment.finalLayout().qualifier());
            }
            VkSubpassDescription.Buffer pSubpasses = VkSubpassDescription.calloc(createInfo.subpasses().size(), stack);
            for (int i = 0; i < createInfo.subpasses().size(); i++) {
                VulkanicSubpassDescription subpass = createInfo.subpasses().get(i);

                VkAttachmentReference.Buffer pInputAttachments = VkAttachmentReference.calloc(subpass.inputAttachments().size(), stack);
                for (int j = 0; j < subpass.inputAttachments().size(); j++) {
                    pInputAttachments.get(j).set(subpass.inputAttachments().get(j).attachment(),subpass.inputAttachments().get(j).layout().qualifier());
                }

                VkAttachmentReference.Buffer pColorAttachments = VkAttachmentReference.calloc(subpass.colorAttachments().size(), stack);
                for (int j = 0; j < subpass.colorAttachments().size(); j++) {
                    pColorAttachments.get(j).set(subpass.colorAttachments().get(j).attachment(),subpass.colorAttachments().get(j).layout().qualifier());
                }

                VkAttachmentReference.Buffer pResolveAttachments;
                if (subpass.resolveAttachments() != null) {
                    pResolveAttachments = VkAttachmentReference.calloc(subpass.resolveAttachments().size(), stack);
                    for (int j = 0; j < subpass.inputAttachments().size(); j++) {
                        pResolveAttachments.get(j).set(subpass.resolveAttachments().get(j).attachment(), subpass.resolveAttachments().get(j).layout().qualifier());
                    }
                } else pResolveAttachments = null;

                IntBuffer pPreserveAttachments = stack.callocInt(subpass.preserveAttachments().size());
                for (int j = 0; j < subpass.preserveAttachments().size(); j++) {
                    pPreserveAttachments.put(j, subpass.preserveAttachments().get(j));
                }

                pSubpasses.get(i)
                        .flags(subpass.flags().mask())
                        .pipelineBindPoint(subpass.pipelineBindPoint().qualifier())
                        .pInputAttachments(pInputAttachments)
                        .pColorAttachments(pColorAttachments)
                        .pResolveAttachments(pResolveAttachments)
                        .pDepthStencilAttachment(subpass.depthStencilAttachment() != null ? VkAttachmentReference.calloc(stack).set(subpass.depthStencilAttachment().attachment(), subpass.depthStencilAttachment().layout().qualifier()) : null)
                        .pPreserveAttachments(pPreserveAttachments);
            }

            VkSubpassDependency.Buffer pDependencies = VkSubpassDependency.calloc(createInfo.dependencies().size(), stack);
            for (int i = 0; i < createInfo.dependencies().size(); i++) {
                VulkanicSubpassDependency dependency = createInfo.dependencies().get(i);
                pDependencies.get(i)
                        .srcSubpass(dependency.srcSubpass())
                        .dstSubpass(dependency.dstSubpass())
                        .srcStageMask(Math.toIntExact(dependency.srcStageMask().mask())) // NOTE: it is assumed that all flags in this mask are *legacy* flags and can be safely cast down to an int without side effects.
                        .dstStageMask(Math.toIntExact(dependency.dstStageMask().mask()))
                        .srcAccessMask(Math.toIntExact(dependency.srcAccessMask().mask()))
                        .dstAccessMask(Math.toIntExact(dependency.dstAccessMask().mask()))
                        .dependencyFlags(dependency.dependencyFlags().mask());
            }

            LongBuffer pRenderPass = stack.callocLong(1);
            VkRenderPassCreateInfo pCreateInfo = VkRenderPassCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(createInfo.flags().mask())
                    .pAttachments(pAttachments)
                    .pSubpasses(pSubpasses)
                    .pDependencies(pDependencies);

            VkUtil.check(VK10.vkCreateRenderPass(this.handle, pCreateInfo, null, pRenderPass));
            return new VulkanicRenderPass(this, pRenderPass.get(0));
        }
    }

    /// Destroys a render pass.
    /// @see VulkanicRenderPass
    public void destroyRenderPass(@NotNull VulkanicRenderPass renderPass) {
        VK10.vkDestroyRenderPass(this.handle, renderPass.handle(), null);
    }

    /// Creates a compute pipeline.
    /// Compute pipelines generally operate on data directly and are invoked via [VulkanicCommandBuffer#dispatch] or [VulkanicCommandBuffer#dispatchIndirect] (after binding with [VulkanicCommandBuffer#bindPipeline])
    /// Good for transforming data, analysis, or other computing things.
    /// @see VulkanicComputePipeline
    public @NotNull VulkanicComputePipeline createComputePipeline(@NotNull VulkanicComputePipelineCreateInfo createInfo, @Nullable VulkanicPipelineCache pipelineCache) throws VulkanException {
        createInfo.validate(features);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pPipeline = stack.callocLong(1);
            VkComputePipelineCreateInfo.Buffer pCreateInfos = VkComputePipelineCreateInfo.calloc(1,stack)
                            .apply(struct -> createInfo.build(struct, stack));
            pCreateInfos.flip();

            VkUtil.check(VK10.vkCreateComputePipelines(handle, pipelineCache != null ? pipelineCache.handle() : VK10.VK_NULL_HANDLE, pCreateInfos, null, pPipeline));
            return new VulkanicComputePipeline(this, pPipeline.get(0), createInfo);
        }
    }

    /// Creates a graphics pipeline.
    /// Graphic pipelines generally render something to the screen with primitives.
    /// After being bound with [VulkanicCommandBuffer#bindPipeline], how they are invoked depends on the method of rendering used.
    /// With standard raster rendering, one can use any of:
    /// - [VulkanicCommandBuffer#draw]
    /// - [VulkanicCommandBuffer#drawIndexed]
    /// - [VulkanicCommandBuffer#drawIndexedIndirect]
    ///
    /// With mesh shading, one can use any of:
    /// - [VulkanicCommandBuffer#drawMeshTasksEXT]
    /// - [VulkanicCommandBuffer#drawMeshTasksIndirectEXT]
    /// - [VulkanicCommandBuffer#drawMeshTasksIndirectCountEXT]
    public @NotNull VulkanicGraphicsPipeline createGraphicsPipeline(
            @NotNull VulkanicGraphicsPipelineCreateInfo createInfo,
            @Nullable VulkanicPipelineCache pipelineCache
    ) throws VulkanException {
        createInfo.validate(features);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pPipeline = stack.callocLong(1);
            VkGraphicsPipelineCreateInfo.Buffer pCreateInfos = VkGraphicsPipelineCreateInfo.calloc(1, stack)
                    .apply(struct -> createInfo.build(struct, stack));
            pCreateInfos.flip();

            VkUtil.check(VK10.vkCreateGraphicsPipelines(handle, pipelineCache != null ? pipelineCache.handle() : VK10.VK_NULL_HANDLE, pCreateInfos, null, pPipeline));

            return new VulkanicGraphicsPipeline(this, pPipeline.get(0), createInfo);
        }
    }

    /// Creates a graphics pipeline builder designed to be used with render passes.
    ///
    /// For dynamic rendering:
    /// @see createGraphicsPipelineBuilder(VulkanicPipelineLayout, VulkanicPipelineRenderingInfo)
    /// @see createGraphicsPipeline
    public @NotNull VulkanicGraphicsPipelineBuilder createGraphicsPipelineBuilder(@NotNull VulkanicPipelineLayout layout, @NotNull VulkanicRenderPass renderPass) {
        return new VulkanicGraphicsPipelineBuilder(this, layout, renderPass);
    }

    /// Creates a graphics pipeline builder
    /// @see createGraphicsPipeline
    public @NotNull VulkanicGraphicsPipelineBuilder createGraphicsPipelineBuilder(@NotNull VulkanicPipelineLayout layout, @NotNull VulkanicPipelineRenderingInfo renderingInfo) {
        return new VulkanicGraphicsPipelineBuilder(this, layout, renderingInfo);
    }

    /// Destroy a pipeline.
    public void destroyPipeline(@NotNull VulkanicPipeline pipeline) {
        VK10.vkDestroyPipeline(handle, pipeline.handle(), null);
    }

    /// Create a Vulkan swapchain.
    /// Swapchains handle presentation in a cross-platform way.
    /// Certain platform-dependant solutions to presentation exist as well and sometimes provide better results.
    /// @see VulkanicSwapchain
    @SuppressWarnings("resource")
    public @NotNull VulkanicSwapchain createSwapchain(
            @NotNull VulkanicSwapchainCreateInfo createInfo
    ) throws VulkanException {
        if (!enabledExtensions.contains(KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME)) throw new UnsupportedOperationException("VulkanicDevice#createSwapchain requires a VK_KHR_swapchain");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pSwapchain = stack.callocLong(1);
            VkSwapchainCreateInfoKHR swapchainCreateInfoKHR = VkSwapchainCreateInfoKHR.calloc(stack)
                    .sType$Default()
                    .flags(createInfo.flags().mask())
                    .surface(createInfo.surface().handle())
                    .minImageCount(createInfo.minImageCount())
                    .imageFormat(createInfo.imageFormat().qualifier())
                    .imageColorSpace(createInfo.imageColorSpace().qualifier())
                    .imageExtent(ex -> ex.set(createInfo.extent().x(), createInfo.extent().y()))
                    .imageArrayLayers(createInfo.imageArrayLayers())
                    .imageUsage(createInfo.imageUsage().mask())
                    .imageSharingMode(createInfo.imageSharingMode().qualifier())
                    .queueFamilyIndexCount(createInfo.queueFamilyIndices() != null ? createInfo.queueFamilyIndices().length : 0)
                    .pQueueFamilyIndices(createInfo.queueFamilyIndices() != null ? stack.ints(createInfo.queueFamilyIndices()) : null)
                    .preTransform(createInfo.preTransform().flag())
                    .compositeAlpha(createInfo.compositeAlpha().flag())
                    .presentMode(createInfo.presentMode().qualifier())
                    .clipped(createInfo.clipped())
                    .oldSwapchain(createInfo.oldSwapchain() != null ? createInfo.oldSwapchain().handle() : 0);
            VulkanicResult result = VulkanicResult.valueOf(KHRSwapchain.vkCreateSwapchainKHR(this.handle, swapchainCreateInfoKHR, null, pSwapchain));
            if (!result.success()) throw new VulkanException(result);
            return new VulkanicSwapchain(this, createInfo, pSwapchain.get(0));
        }
    }

    /// Destroys a swapchain.
    /// @see VulkanicSwapchain
    /// @see createSwapchain
    public void destroySwapchain(@NotNull VulkanicSwapchain swapchain) {
        KHRSwapchain.vkDestroySwapchainKHR(this.handle, swapchain.handle(), null);
    }

    /// Creates a sampler.
    /// Samplers tell the GPU how to sample information from a texture.
    /// This handles things like anisotropy, mipmaps, and filtering.
    /// @see VulkanicSampler
    public @NotNull VulkanicSampler createSampler(VulkanicSamplerCreateInfo createInfo) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkSamplerCreateInfo pCreateInfo = VkSamplerCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(createInfo.flags().mask())
                    .magFilter(createInfo.magFilter().qualifier()).minFilter(createInfo.minFilter().qualifier())
                    .mipmapMode(createInfo.mipmapMode().qualifier())
                    .addressModeU(createInfo.addressModeU().qualifier()).addressModeV(createInfo.addressModeV().qualifier()).addressModeW(createInfo.addressModeW().qualifier())
                    .mipLodBias(createInfo.mipLodBias())
                    .anisotropyEnable(createInfo.anisotropyEnable()).maxAnisotropy(createInfo.maxAnisotropy())
                    .compareEnable(createInfo.compareEnable()).compareOp(createInfo.compareOp() != null ? createInfo.compareOp().qualifier() : 0)
                    .minLod(createInfo.minLod()).maxLod(createInfo.maxLod())
                    .borderColor(createInfo.borderColor() != null ? createInfo.borderColor().qualifier() : 0)
                    .unnormalizedCoordinates(createInfo.unnormalizedCoordinates());

            LongBuffer pSampler = stack.callocLong(1);
            VkUtil.check(VK10.vkCreateSampler(this.handle, pCreateInfo, null, pSampler));
            return new VulkanicSampler(this, createInfo, pSampler.get(0));
        }
    }

    /// Destroys a sampler.
    /// @see VulkanicSampler
    /// @see createSampler
    public void destroySampler(@NotNull VulkanicSampler sampler) {
        VK10.vkDestroySampler(this.handle, sampler.handle(), null);
    }

    /// Creates a query pool.
    /// Query pools are pools for query objects, which allow you to measure various things about your program and the GPU, such as invocations of various shader programs.
    /// @see VulkanicQueryPool
    public @NotNull VulkanicQueryPool createQueryPool(@NotNull EnumIntBitset<VulkanicQueryPoolCreateFlag> flags, @NotNull VulkanicQueryType type, int queryCount, @NotNull EnumIntBitset<VulkanicQueryPipelineStatisticFlag> pipelineStatistics) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pQueryPool = stack.callocLong(1);
            VkQueryPoolCreateInfo pQueryPoolCreateInfo = VkQueryPoolCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(flags.mask())
                    .queryType(type.qualifier())
                    .queryCount(queryCount)
                    .pipelineStatistics(pipelineStatistics.mask());

            VkUtil.check(VK10.vkCreateQueryPool(this.handle, pQueryPoolCreateInfo, null, pQueryPool));
            return new VulkanicQueryPool(this, pQueryPool.get(0), queryCount);
        }
    }

    /// Destroys a query pool.
    /// @see VulkanicQueryPool
    /// @see createQueryPool
    public void destroyQueryPool(@NotNull VulkanicQueryPool pool) {
        VK10.vkDestroyQueryPool(this.handle, pool.handle(), null);
    }

    /// Requests query pool results to be copied to host memory.
    ///
    /// See [VulkanicCommandBuffer#copyQueryPoolResults] for more control over copying.
    /// @see VulkanicQueryPool
    public @NotNull VulkanicResult getQueryPoolResults(@NotNull VulkanicQueryPool queryPool, int firstQuery, int queryCount, @NotNull ByteBuffer pData, long stride, @NotNull EnumIntBitset<VulkanicQueryResultFlag> flags) {
        return VulkanicResult.valueOf(VK10.vkGetQueryPoolResults(this.handle, queryPool.handle(), firstQuery, queryCount, pData, stride, flags.mask()));
    }

    /// Resets a query pool.
    /// If the hostQueryReset feature is available, this can be called to reset queries. Otherwise, see [VulkanicCommandBuffer#resetQueryPool]
    /// @see VulkanicQueryPool
    public void resetQueryPool(@NotNull VulkanicQueryPool queryPool, int firstQuery, int queryCount) {
        if (!features.supportsHostQueryReset()) {
            throw new UnsupportedOperationException("VulkanicDevice#resetQueryPool requires either Vulkan 1.2 or EXTHostQueryReset and the hostQueryReset feature set.");
        }

        if (instance.applicationInfo().apiVersion().version() >= ApiVersion.VULKAN_1_2.version()) {
            VK12.vkResetQueryPool(this.handle, queryPool.handle(), firstQuery, queryCount);
        } else {
            EXTHostQueryReset.vkResetQueryPoolEXT(this.handle, queryPool.handle(), firstQuery, queryCount);
        }
    }

    /// Writes descriptor heap resources.
    /// @apiNote Tensor resource descriptors are not currently supported
    @SuppressWarnings("resource")
    public @NotNull VulkanicResult writeResourceDescriptors(List<VulkanicResourceDescriptorInfo> resources, List<ByteBuffer> descriptors) {
        if (!features.supportsDescriptorHeap()) {
            throw new UnsupportedOperationException("VulkanicDevice#writeResourceDescriptors requires the descriptorHeap feature.");
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkResourceDescriptorInfoEXT.Buffer pResources = VkResourceDescriptorInfoEXT.calloc(resources.size(), stack);
            for (int i = 0; i < resources.size(); i++) {
                VulkanicResourceDescriptorInfo info = resources.get(i);
                VkResourceDescriptorDataEXT pData = VkResourceDescriptorDataEXT.calloc(stack);
                switch (info.data()) {
                    case VulkanicResourceDescriptorData.Image image -> pData.pImage(VkImageDescriptorInfoEXT.calloc(stack)
                            .sType$Default()
                            .pView(VkImageViewCreateInfo.calloc(stack)
                                    .sType$Default()
                                    .viewType(image.view().viewType().qualifier())
                                    .format(image.view().format().qualifier())
                                    .components(mapping -> mapping.set(image.view().componentMapping().r().qualifier(), image.view().componentMapping().g().qualifier(), image.view().componentMapping().b().qualifier(), image.view().componentMapping().a().qualifier()))
                                    .subresourceRange(subresource -> subresource.set(image.view().subresourceRange().aspectMask().mask(), image.view().subresourceRange().baseMipLevel(), image.view().subresourceRange().levelCount(), image.view().subresourceRange().baseArrayLayer(), image.view().subresourceRange().layerCount()))
                            )
                            .layout(image.layout().qualifier())
                    );
                    case VulkanicResourceDescriptorData.TexelBuffer texelBuffer -> pData.pTexelBuffer(VkTexelBufferDescriptorInfoEXT.calloc(stack)
                            .sType$Default()
                            .format(texelBuffer.format().qualifier())
                            .addressRange(range -> range.address$(texelBuffer.addressRange().address()).size(texelBuffer.addressRange().size().bytes()))
                    );
                    case VulkanicDeviceAddressRange deviceAddressRange -> pData.pAddressRange(VkDeviceAddressRangeEXT.calloc(stack)
                            .address$(deviceAddressRange.address())
                            .size(deviceAddressRange.size().bytes())
                    );
                    case VulkanicResourceDescriptorData.TensorViewCreateInfo _ ->
                        throw new UnsupportedOperationException("VulkanicResourceDescriptorData#TensorViewCreateInfo is not currently supported");
                    // TODO: determine whether this is worth it to implement?
                }

                pResources.get(i)
                        .sType$Default()
                        .type(info.type().qualifier())
                        .data(pData);
            }
            VkHostAddressRangeEXT.Buffer pDescriptors = VkHostAddressRangeEXT.calloc(descriptors.size(), stack);
            for (int i = 0; i < descriptors.size(); i++) {
                ByteBuffer buffer = descriptors.get(i);
                pDescriptors.get(i).address$(buffer);
            }

            return VulkanicResult.valueOf(EXTDescriptorHeap.vkWriteResourceDescriptorsEXT(this.handle, pResources, pDescriptors));
        }
    }

    /// Writes descriptor heap samplers.
    @SuppressWarnings("resource")
    public @NotNull VulkanicResult writeSamplerDescriptors(List<VulkanicSamplerCreateInfo> samplers, List<ByteBuffer> descriptors) {
        if (!features.supportsDescriptorHeap()) {
            throw new UnsupportedOperationException("VulkanicDevice#writeResourceDescriptors requires the descriptorHeap feature.");
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkSamplerCreateInfo.Buffer pSamplers = VkSamplerCreateInfo.calloc(samplers.size(), stack);
            for (int i = 0; i < samplers.size(); i++) {
                VulkanicSamplerCreateInfo samplerInfo = samplers.get(i);
                samplerInfo.populate(pSamplers.get(i));
            }
            VkHostAddressRangeEXT.Buffer pDescriptors = VkHostAddressRangeEXT.calloc(descriptors.size(), stack);
            for (int i = 0; i < descriptors.size(); i++) {
                ByteBuffer buffer = descriptors.get(i);
                pDescriptors.get(i).address$(buffer);
            }

            return VulkanicResult.valueOf(EXTDescriptorHeap.vkWriteSamplerDescriptorsEXT(this.handle, pSamplers, pDescriptors));
        }
    }

    /// Captures the image opaque capture descriptor data for a descriptor heap replay.
    @SuppressWarnings({"resource", "SpellCheckingInspection"})
    public @NotNull VulkanicResult getImageOpaqueCaptureData(List<VulkanicImage> images, List<VulkanicHostAddressRange> datas) {
        if (!features.supportsDescriptorHeapCaptureReplay()) {
            throw new UnsupportedOperationException("VulkanicDevice#getImageOpaqueCaptureData requires the descriptorHeapCaptureReplay feature.");
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pImages = stack.callocLong(images.size());
            for (VulkanicImage image : images) {
                pImages.put(image.handle());
            }
            pImages.flip();

            VkHostAddressRangeEXT.Buffer pDatas = VkHostAddressRangeEXT.calloc(datas.size(), stack);
            for (int i = 0; i < datas.size(); i++) {
                pDatas.get(i).address$(datas.get(i).data());
            }

            return VulkanicResult.valueOf(EXTDescriptorHeap.vkGetImageOpaqueCaptureDataEXT(this.handle, pImages, pDatas));
        }
    }

    /// Queries the size of a given descriptor type from the GPU. Only necessary for descriptor heaps.
    public @NotNull VulkanicDeviceSize getPhysicalDeviceDescriptorSize(@NotNull VulkanicDescriptorType type) {
        if (!features.supportsDescriptorHeap()) {
            throw new UnsupportedOperationException("VulkanicDevice#getPhysicalDeviceDescriptorSize requires the descriptorHeap feature.");
        }

        return VulkanicDeviceSize.ofBytes(EXTDescriptorHeap.vkGetPhysicalDeviceDescriptorSizeEXT(this.physicalDevice.handle(), type.qualifier()));
    }

    /// Creates a pipeline cache from initial data (or an empty one if initialData is null).
    /// This can be used to speed up pipeline compilation times for subsequent application starts.
    /// Note: pipeline caches are device-specific, they cannot be simply transferred between devices and should not be shipped.
    /// @see VulkanicPipelineCache
    public @NotNull VulkanicPipelineCache createPipelineCache(@NotNull EnumIntBitset<VulkanicPipelineCacheCreateFlag> flags, @Nullable ByteBuffer initialData) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkPipelineCacheCreateInfo createInfo = VkPipelineCacheCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(flags.mask())
                    .pInitialData(initialData);

            LongBuffer pCache = stack.callocLong(1);
            VkUtil.check(VK10.vkCreatePipelineCache(this.handle, createInfo, null, pCache));

            return new VulkanicPipelineCache(this, pCache.get(0));
        }
    }

    /// Destroys a pipeline cache
    /// @see VulkanicPipelineCache
    public void destroyPipelineCache(@NotNull VulkanicPipelineCache cache) {
        VK10.vkDestroyPipelineCache(this.handle, cache.handle(), null);
    }

    /// Merges several source caches into a single destination cache.
    /// @see VulkanicPipelineCache
    public @NotNull VulkanicResult mergePipelineCaches(@NotNull VulkanicPipelineCache dstCache, @NotNull List<VulkanicPipelineCache> srcCaches) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            return VulkanicResult.valueOf(VK10.vkMergePipelineCaches(this.handle, dstCache.handle(), stack.longs(srcCaches.stream().mapToLong(VulkanicPipelineCache::handle).toArray())));
        }
    }

    /// Queries the size of the data contained within a pipeline cache (for pre-allocation when saving.)
    /// @see VulkanicPipelineCache
    /// @see getPipelineCacheData
    public long getPipelineCacheDataSize(@NotNull VulkanicPipelineCache cache) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer pDataSize = stack.callocPointer(1);
            VkUtil.check(VK10.vkGetPipelineCacheData(this.handle, cache.handle(), pDataSize, null));
            return pDataSize.get(0);
        }
    }

    /// Copies the data from the pipeline cache into the given [ByteBuffer].
    /// The given buffer must be large enough to contain the data, see [#getPipelineCacheDataSize]
    /// @see VulkanicPipelineCache
    @Contract(mutates = "param2", value = "_, _ -> _")
    public @NotNull VulkanicResult getPipelineCacheData(@NotNull VulkanicPipelineCache cache, @NotNull ByteBuffer buffer) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer pDataSize = stack.pointers(buffer.remaining());
            return VulkanicResult.valueOf(VK10.vkGetPipelineCacheData(this.handle, cache.handle(), pDataSize, buffer));
        }
    }

    // TODO: deferred operation
    /// Creates a ray tracing pipeline.
    /// Note: this API isn't really finished.
    @ApiStatus.Experimental
    public @NotNull VulkanicRayTracingPipeline createRayTracingPipeline(
            @NotNull VulkanicRayTracingPipelineCreateInfo createInfo,
            @Nullable VulkanicPipelineCache cache
    ) throws VulkanException {
        createInfo.validate(features);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pOut = stack.callocLong(1);
            VkRayTracingPipelineCreateInfoKHR.Buffer pCreateInfos = VkRayTracingPipelineCreateInfoKHR.calloc(1, stack)
                    .apply(struct -> createInfo.build(struct, stack));
            pCreateInfos.flip();

            VkUtil.check(KHRRayTracingPipeline.vkCreateRayTracingPipelinesKHR(this.handle, 0, cache != null ? cache.handle() : 0, pCreateInfos, null, pOut));
            return new VulkanicRayTracingPipeline(this, pOut.get(0), createInfo);
        }
    }

    public void freeMemory(VulkanicDeviceMemory memory) {
        VK11.vkFreeMemory(this.handle, memory.handle(), null);
    }

    public VulkanicDeviceMemory allocateMemory(VulkanicMemoryAllocateInfo info) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkMemoryAllocateInfo pAllocateInfo = VkMemoryAllocateInfo.calloc(stack)
                    .sType$Default()
                    .allocationSize(info.allocationSize().bytes())
                    .memoryTypeIndex(info.memoryTypeIndex());

            if (info.importInfo() != null) {
                switch (info.importInfo()) {
                    case VulkanicImportInfo.Win32HandleKHR win32 ->
                        pAllocateInfo.pNext(VkImportMemoryWin32HandleInfoKHR.calloc(stack)
                                .sType$Default()
                                .handleType(win32.handleType().flag())
                                .handle(win32.handle())
                                .name(stack.UTF16(win32.name())));
                    case VulkanicImportInfo.FdKHR fd ->
                        pAllocateInfo.pNext(VkImportMemoryFdInfoKHR.calloc(stack)
                                .sType$Default()
                                .handleType(fd.handleType().flag())
                                .fd(fd.fd()));
                    case VulkanicImportInfo.HostPointerEXT host ->
                        pAllocateInfo.pNext(VkImportMemoryHostPointerInfoEXT.calloc(stack)
                                .sType$Default()
                                .handleType(host.handleType().flag())
                                .pHostPointer(host.pHostPointer()));
                    case VulkanicImportInfo.HardwareBufferANDROID awhb ->
                        pAllocateInfo.pNext(VkImportAndroidHardwareBufferInfoANDROID.calloc(stack)
                                .sType$Default()
                                .buffer(awhb.pBuffer()));
                    case VulkanicImportInfo.MetalHandleEXT metal ->
                        pAllocateInfo.pNext(VkImportMemoryMetalHandleInfoEXT.calloc(stack)
                                .sType$Default()
                                .handleType(metal.handleType().flag())
                                .handle(metal.pHandle()));
                }
            }

            LongBuffer pMemory = stack.callocLong(1);
            VkUtil.check(VK11.vkAllocateMemory(this.handle, pAllocateInfo, null, pMemory));
            return new VulkanicDeviceMemory(this, pMemory.get(0));
        }
    }

    @SuppressWarnings("resource")
    public void bindBufferMemory(List<VulkanicBindBufferMemoryInfo> binds) throws VulkanException {
        if (this.instance.applicationInfo().apiVersion().version() >= ApiVersion.VULKAN_1_1.version()) {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                VkBindBufferMemoryInfo.Buffer pBinds = VkBindBufferMemoryInfo.calloc(binds.size(), stack);
                for (int i = 0; i < binds.size(); i++) {
                    VulkanicBindBufferMemoryInfo bind = binds.get(i);
                    pBinds.get(i)
                            .sType$Default()
                            .buffer(bind.buffer().handle())
                            .memory(bind.memory().handle())
                            .memoryOffset(bind.memoryOffset().bytes());
                }
                VkUtil.check(VK11.vkBindBufferMemory2(this.handle, pBinds));
            }
        } else {
            for (VulkanicBindBufferMemoryInfo bind : binds) {
                VkUtil.check(VK10.vkBindBufferMemory(this.handle, bind.buffer().handle(), bind.memory().handle(), bind.memoryOffset().bytes()));
            }
        }
    }

    public @NotNull MappedMemory mapMemory(@NotNull VulkanicDeviceMemory memory, @NotNull VulkanicDeviceSize offset, @NotNull VulkanicDeviceSize size, @NotNull EnumIntBitset<VulkanicMemoryMapFlags> flags) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer ppData = stack.callocPointer(1);
            VkUtil.check(VK10.vkMapMemory(this.handle, memory.handle(), offset.bytes(), size.bytes(), flags.mask(), ppData));
            return new DeviceMappedMemory(this, memory, ppData.get(0), size.bytes());
        }
    }

    public void unmapMemory(@NotNull VulkanicDeviceMemory memory) {
        VK10.vkUnmapMemory(this.handle, memory.handle());
    }

    /// Creates, records, and submits a transient command buffer then returns a CompletableFuture that completes when the fence returned by the submission is finished.
    public @NotNull CompletableFuture<Void> submitTransient(
            @NotNull VulkanicCommandPool pool,
            @NotNull VulkanicQueue queue,
            @NotNull Consumer<VulkanicCommandBuffer> usage
    ) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        VulkanicCommandBuffer commandBuffer = null;
        VulkanicFence fence = null;
        try {
            commandBuffer = allocateCommandBuffer(pool, VulkanicCommandBufferLevel.PRIMARY);
            VulkanicResult result = commandBuffer.begin(new VulkanicCommandBufferBeginInfo(EnumIntBitset.of(VulkanicCommandBufferUsageFlag.ONE_TIME_SUBMIT), null));
            if (!result.success()) {
                throw new VulkanException(result);
            }

            usage.accept(commandBuffer);

            result = commandBuffer.end();
            if (!result.success()) {
                throw new VulkanException(result);
            }

            fence = createFence(EnumIntBitset.of());

            result = queue.submit(fence, List.of(), List.of(), commandBuffer);
            if (!result.success()) {
                throw new VulkanException(result);
            }

            final VulkanicFence submittedFence = fence;
            final VulkanicCommandBuffer submittedCommandBuffer = commandBuffer;
            Thread.ofVirtual().start(() -> {
                try {
                    submittedFence.waitFor(VkUtil.FOREVER);
                    future.complete(null);
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            });

            return future.whenComplete((_, _) -> {
                submittedFence.close();
                submittedCommandBuffer.close();
            });
        } catch (Exception e) {
            if (commandBuffer != null) commandBuffer.close();
            if (fence != null) fence.close();

            future.completeExceptionally(e);
            return future;
        }
    }

    /// Creates a transient command pool then calls [#submitTransient(VulkanicCommandPool, VulkanicQueue, Consumer)]
    public @NotNull CompletableFuture<Void> submitTransient(@NotNull VulkanicQueue queue, @NotNull Consumer<VulkanicCommandBuffer> usage) throws VulkanException {
        VulkanicCommandPool pool = createCommandPool(EnumIntBitset.of(VulkanicCommandPoolCreateFlag.TRANSIENT), queue.family());
        return submitTransient(pool, queue, usage).whenComplete((_,_) -> pool.close());
    }

    /// Waits until this device is fully idle (no tasks pending completion)
    @SuppressWarnings("UnusedReturnValue")
    public @NotNull VulkanicResult waitIdle() {
        return VulkanicResult.valueOf(VK11.vkDeviceWaitIdle(this.handle));
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public VkDevice handle() {
        return handle;
    }

    @Override
    public void close() {
        VK11.vkDestroyDevice(this.handle, null);
    }

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.DEVICE;
    }
}
