package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.command.*;
import net.flamgop.vulkanic.core.feature.VulkanicDeviceFeatures;
import net.flamgop.vulkanic.exception.VulkanException;
import net.flamgop.vulkanic.exception.VulkanicResult;
import net.flamgop.vulkanic.memory.VulkanicDeviceSize;
import net.flamgop.vulkanic.memory.VulkanicFormat;
import net.flamgop.vulkanic.memory.image.*;
import net.flamgop.vulkanic.memory.image.sampler.*;
import net.flamgop.vulkanic.pipeline.*;
import net.flamgop.vulkanic.pipeline.VulkanicComputePipeline;
import net.flamgop.vulkanic.pipeline.VulkanicGraphicsPipeline;
import net.flamgop.vulkanic.pipeline.descriptor.*;
import net.flamgop.vulkanic.pipeline.descriptor.heap.*;
import net.flamgop.vulkanic.pipeline.graphics.*;
import net.flamgop.vulkanic.surface.VulkanicSurface;
import net.flamgop.vulkanic.swapchain.*;
import net.flamgop.vulkanic.sync.*;
import net.flamgop.vulkanic.util.EnumIntBitset;
import net.flamgop.vulkanic.util.VkUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3i;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.*;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class VulkanicDevice implements AutoCloseable {

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
            queueFamilies.put(createInfo.queueFamilyIndex(), new VulkanicQueueFamily(this, createInfo.queueFamilyIndex(), createInfo.queueCount()));
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
    /// @param queueCreateInfos A list of queue infos, to prepopulate this device's queue families
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
                queueFamilies.put(createInfo.queueFamilyIndex(), new VulkanicQueueFamily(this, createInfo.queueFamilyIndex(), createInfo.queueCount()));
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

    public boolean supportsExtension(String extension) {
        return enabledExtensions.stream().anyMatch(extension::equals);
    }

    public @NotNull VulkanicPhysicalDevice physicalDevice() {
        return physicalDevice;
    }

    public @NotNull VulkanicDeviceFeatures features() {
        return features;
    }

    /// @apiNote index refers to the actual queue family index as determined during queue family enumeration (i.e., at device creation time)
    /// @param index the queue family index of the queue family
    /// @return the VulkanicQueueFamily for the queue family index, or null if that index was not passed as a VulkanicQueueInfo to this device's constructor
    public @Nullable VulkanicQueueFamily queueFamily(int index) {
        return queueFamilies.get(index);
    }

    @ApiStatus.Internal
    protected @NotNull VulkanicQueue queue(int queueFamilyIndex, int queueIndex) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer pQueue = stack.callocPointer(1);
            VK11.vkGetDeviceQueue(this.handle, queueFamilyIndex, queueIndex, pQueue);
            return new VulkanicQueue(queueFamilyIndex, new VkQueue(pQueue.get(0), this.handle));
        }
    }

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

    public void destroyFence(@NotNull VulkanicFence fence) {
        VK11.vkDestroyFence(this.handle, fence.handle(), null);
    }

    public @NotNull VulkanicResult resetFence(@NotNull VulkanicFence fence) {
        return VulkanicResult.valueOf(VK11.vkResetFences(this.handle, fence.handle()));
    }

    public int resetFences(@NotNull VulkanicFence... fences) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pFences = stack.callocLong(fences.length);
            for (VulkanicFence fence : fences) {
                pFences.put(fence.handle());
            }
            pFences.flip();
            return VK11.vkResetFences(this.handle, pFences);
        }
    }

    public @NotNull VulkanicResult fenceStatus(@NotNull VulkanicFence fence) {
        return VulkanicResult.valueOf(VK11.vkGetFenceStatus(this.handle, fence.handle()));
    }

    public @NotNull VulkanicResult waitForFence(@NotNull VulkanicFence fence, @NotNull Duration timeout) {
        return VulkanicResult.valueOf(VK11.vkWaitForFences(this.handle, fence.handle(), true, timeout.toNanos()));
    }

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

    public @NotNull VulkanicSemaphore createSemaphore(@NotNull VulkanicSemaphoreType type, long initialValue) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkSemaphoreCreateInfo createInfo = VkSemaphoreCreateInfo.calloc(stack)
                    .pNext(VkSemaphoreTypeCreateInfo.calloc(stack)
                            .semaphoreType(type.qualifier())
                            .initialValue(initialValue)
                            .sType$Default())
                    .sType$Default();

            LongBuffer pSemaphore = stack.callocLong(1);
            VkUtil.check(VK11.vkCreateSemaphore(this.handle, createInfo, null, pSemaphore));
            return new VulkanicSemaphore(this, pSemaphore.get(0), type);
        }
    }

    public void destroySemaphore(@NotNull VulkanicSemaphore semaphore) {
        VK11.vkDestroySemaphore(this.handle, semaphore.handle(), null);
    }

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
            return new VulkanicCommandPool(this, pCommandPool.get(0));
        }
    }

    public void destroyCommandPool(@NotNull VulkanicCommandPool commandPool) {
        VK11.vkDestroyCommandPool(this.handle, commandPool.handle(), null);
    }

    public int resetCommandPool(@NotNull VulkanicCommandPool commandPool, int flags) {
        return VK11.vkResetCommandPool(this.handle, commandPool.handle(), flags);
    }

    public @NotNull VulkanicCommandBuffer allocateCommandBuffer(@NotNull VulkanicCommandPool pool, @NotNull VulkanicCommandBufferLevel level) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkCommandBufferAllocateInfo pAllocateInfo = VkCommandBufferAllocateInfo.calloc(stack)
                    .sType$Default()
                    .commandPool(pool.handle())
                    .level(level.qualifier())
                    .commandBufferCount(1);
            PointerBuffer pCommandBuffers = stack.callocPointer(1);
            VkUtil.check(VK11.vkAllocateCommandBuffers(this.handle, pAllocateInfo, pCommandBuffers));
            return new VulkanicCommandBuffer(pool, new VkCommandBuffer(pCommandBuffers.get(0), this.handle));
        }
    }

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
            for (int i = 0; i < count; i++) commandBuffers[i] = new VulkanicCommandBuffer(pool, new VkCommandBuffer(pCommandBuffers.get(i), this.handle));
            return commandBuffers;
        }
    }

    public void freeCommandBuffer(@NotNull VulkanicCommandPool pool, @NotNull VulkanicCommandBuffer commandBuffer) {
        VK11.vkFreeCommandBuffers(this.handle, pool.handle(), commandBuffer.handle());
    }

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

    public @NotNull VulkanicResult beginCommandBuffer(@NotNull VulkanicCommandBuffer commandBuffer, @NotNull VkCommandBufferBeginInfo beginInfo) {
        return VulkanicResult.valueOf(VK11.vkBeginCommandBuffer(commandBuffer.handle(), beginInfo));
    }

    public @NotNull VulkanicResult endCommandBuffer(@NotNull VulkanicCommandBuffer commandBuffer) {
        return VulkanicResult.valueOf(VK11.vkEndCommandBuffer(commandBuffer.handle()));
    }

    public @NotNull VulkanicResult resetCommandBuffer(@NotNull VulkanicCommandBuffer commandBuffer, int flags) {
        return VulkanicResult.valueOf(VK11.vkResetCommandBuffer(commandBuffer.handle(), flags));
    }

    public @NotNull VulkanicImageView createImageView(@NotNull VulkanicImage image, @NotNull VulkanicImageViewCreateInfo imageViewCreateInfo) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pImageView = stack.callocLong(1);
            VkImageViewCreateInfo createInfo = VkImageViewCreateInfo.calloc(stack)
                    .sType$Default()
                    .image(image.handle())
                    .viewType(imageViewCreateInfo.viewType().qualifier())
                    .format(imageViewCreateInfo.format().qualifier())
                    .components(VkComponentMapping.calloc(stack)
                            .r(imageViewCreateInfo.componentMapping().r().qualifier())
                            .g(imageViewCreateInfo.componentMapping().g().qualifier())
                            .b(imageViewCreateInfo.componentMapping().b().qualifier())
                            .a(imageViewCreateInfo.componentMapping().a().qualifier()))
                    .subresourceRange(VkImageSubresourceRange.calloc(stack)
                            .aspectMask(imageViewCreateInfo.subresourceRange().aspectMask().mask())
                            .baseMipLevel(imageViewCreateInfo.subresourceRange().baseMipLevel())
                            .levelCount(imageViewCreateInfo.subresourceRange().levelCount())
                            .baseArrayLayer(imageViewCreateInfo.subresourceRange().baseArrayLayer())
                            .layerCount(imageViewCreateInfo.subresourceRange().layerCount()));
            VkUtil.check(VK11.vkCreateImageView(this.handle, createInfo, null, pImageView));
            return new VulkanicImageView(this, image, pImageView.get(0));
        }
    }

    public void destroyImageView(@NotNull VulkanicImageView imageView) {
        VK11.vkDestroyImageView(this.handle, imageView.handle(), null);
    }

    public @NotNull VulkanicShaderModule createShaderModule(byte @NotNull [] code) throws VulkanException {
        ByteBuffer pCode = MemoryUtil.memAlloc(code.length);
        try {
            pCode.put(code).flip();
            return createShaderModule(pCode);
        } finally {
            MemoryUtil.memFree(pCode);
        }
    }

    public @NotNull VulkanicShaderModule createShaderModule(@NotNull ByteBuffer pCode) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pShaderModule = stack.callocLong(1);
            VkUtil.check(VK10.vkCreateShaderModule(handle, VkShaderModuleCreateInfo.calloc(stack)
                    .sType$Default()
                    .pCode(pCode), null, pShaderModule));
            return new VulkanicShaderModule(this, pShaderModule.get(0));
        }
    }

    public void destroyShaderModule(@NotNull VulkanicShaderModule module) {
        VK10.vkDestroyShaderModule(handle, module.handle(), null);
    }

    @Deprecated(forRemoval = true)
    public @NotNull VulkanicPipelineLayout createPipelineLayout(@NotNull VkPipelineLayoutCreateInfo createInfo) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pPipelineLayout = stack.callocLong(1);
            VkUtil.check(VK10.vkCreatePipelineLayout(handle, createInfo, null, pPipelineLayout));
            VkPushConstantRange.Buffer pushConstantRanges = createInfo.pPushConstantRanges();
            return new VulkanicPipelineLayout(this, pPipelineLayout.get(0), pushConstantRanges != null && pushConstantRanges.capacity() > 0 ? pushConstantRanges.get(0).size() : 0);
        }
    }

    @SuppressWarnings("resource")
    public @NotNull VulkanicPipelineLayout createPipelineLayout(@NotNull List<@NotNull VulkanicDescriptorSetLayout> setLayouts, @NotNull List<@NotNull VulkanicPushConstantRange> pushConstantRanges) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkPushConstantRange.Buffer pPushConstantRanges = VkPushConstantRange.calloc(pushConstantRanges.size(), stack);
            for (int i = 0; i < pushConstantRanges.size(); i++) {
                VulkanicPushConstantRange pushConstantRange = pushConstantRanges.get(i);
                pPushConstantRanges.get(i)
                        .stageFlags(pushConstantRange.stageFlags().mask())
                        .offset(pushConstantRange.offset())
                        .size((int) pushConstantRange.size().bytes()); // note: push constants can't actually be larger than like 512 bytes usually
            }

            LongBuffer pSetLayouts = stack.callocLong(setLayouts.size());
            for (int i = 0; i < setLayouts.size(); i++) {
                pSetLayouts.put(i, setLayouts.get(i).handle());
            }

            VkPipelineLayoutCreateInfo createInfo = VkPipelineLayoutCreateInfo.calloc(stack)
                    .sType$Default()
                    .pPushConstantRanges(pPushConstantRanges)
                    .pSetLayouts(pSetLayouts)
                    .setLayoutCount(setLayouts.size());

            LongBuffer pPipelineLayout = stack.callocLong(1);
            VkUtil.check(VK10.vkCreatePipelineLayout(handle, createInfo, null, pPipelineLayout));
            return new VulkanicPipelineLayout(this, pPipelineLayout.get(0), pPushConstantRanges.capacity() > 0 ? pPushConstantRanges.get(0).size() : 0);
        }
    }

    public void destroyPipelineLayout(@NotNull VulkanicPipelineLayout layout) {
        VK10.vkDestroyPipelineLayout(handle, layout.handle(), null);
    }

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

    public void destroyDescriptorSetLayout(@NotNull VulkanicDescriptorSetLayout descriptorSetLayout) {
        VK10.vkDestroyDescriptorSetLayout(handle, descriptorSetLayout.handle(), null);
    }

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

    @SuppressWarnings("resource")
    public @NotNull VulkanicDescriptorPool createDescriptorPool(
            @NotNull EnumIntBitset<VulkanicDescriptorPoolCreateFlag> flags,
            int maxSets,
            @NotNull List<VulkanicDescriptorPoolSize> poolSizes
    ) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkDescriptorPoolSize.Buffer pPoolSizes = VkDescriptorPoolSize.calloc(poolSizes.size(), stack);
            for (int i = 0; i < poolSizes.size(); i++) {
                VulkanicDescriptorPoolSize poolSize = poolSizes.get(i);
                pPoolSizes.get(i)
                        .type(poolSize.type().qualifier())
                        .descriptorCount(poolSize.descriptorCount());
            }

            VkDescriptorPoolCreateInfo createInfo = VkDescriptorPoolCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(flags.mask())
                    .maxSets(maxSets)
                    .pPoolSizes(pPoolSizes);
            LongBuffer pDescriptorPool = stack.callocLong(1);
            VkUtil.check(VK10.vkCreateDescriptorPool(handle, createInfo, null, pDescriptorPool));
            return new VulkanicDescriptorPool(this, pDescriptorPool.get(0));
        }
    }

    public void destroyDescriptorPool(@NotNull VulkanicDescriptorPool descriptorPool) {
        VK10.vkDestroyDescriptorPool(handle, descriptorPool.handle(), null);
    }

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
                descriptorSets[i] = new VulkanicDescriptorSet(this, pDescriptorSet.get(i));
            }
            return descriptorSets;
        }
    }

    @ApiStatus.Experimental
    public @NotNull VulkanicRenderPass createRenderPass(@NotNull VkRenderPassCreateInfo createInfo) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pRenderPass = stack.callocLong(1);
            VkUtil.check(VK10.vkCreateRenderPass(handle, createInfo, null, pRenderPass));
            return new VulkanicRenderPass(this, pRenderPass.get(0));
        }
    }

    public void destroyRenderPass(@NotNull VulkanicRenderPass renderPass) {
        VK10.vkDestroyRenderPass(this.handle, renderPass.handle(), null);
    }

    public @NotNull VulkanicComputePipeline createComputePipeline(@NotNull VulkanicComputePipelineCreateInfo createInfo, @Nullable VulkanicPipelineCache pipelineCache) throws VulkanException {
        createInfo.validate(features);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pPipeline = stack.callocLong(1);
            VkComputePipelineCreateInfo.Buffer pCreateInfo = VkComputePipelineCreateInfo.calloc(1,stack)
                            .apply(struct -> createInfo.build(struct, stack));

            VkUtil.check(VK10.vkCreateComputePipelines(handle, pipelineCache != null ? pipelineCache.handle() : VK10.VK_NULL_HANDLE, pCreateInfo, null, pPipeline));
            return new VulkanicComputePipeline(this, pPipeline.get(0));
        }
    }

    public @NotNull VulkanicGraphicsPipeline createGraphicsPipeline(
            @NotNull VulkanicGraphicsPipelineCreateInfo createInfo,
            @Nullable VulkanicPipelineCache pipelineCache
    ) throws VulkanException {
        createInfo.validate(features);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pPipeline = stack.callocLong(1);
            VkGraphicsPipelineCreateInfo.Buffer pCreateInfos = VkGraphicsPipelineCreateInfo.calloc(1, stack)
                    .apply(struct -> createInfo.build(struct, stack));

            VkUtil.check(VK10.vkCreateGraphicsPipelines(handle, pipelineCache != null ? pipelineCache.handle() : VK10.VK_NULL_HANDLE, pCreateInfos, null, pPipeline));

            return new VulkanicGraphicsPipeline(this, pPipeline.get(0));
        }
    }

    public @NotNull VulkanicGraphicsPipelineBuilder createGraphicsPipelineBuilder(@NotNull VulkanicPipelineLayout layout, @NotNull VulkanicRenderPass renderPass) {
        return new VulkanicGraphicsPipelineBuilder(this, layout, renderPass);
    }

    public @NotNull VulkanicGraphicsPipelineBuilder createGraphicsPipelineBuilder(@NotNull VulkanicPipelineLayout layout, @NotNull VulkanicPipelineRenderingInfo renderingInfo) {
        return new VulkanicGraphicsPipelineBuilder(this, layout, renderingInfo);
    }

    public void destroyPipeline(@NotNull VulkanicPipeline pipeline) {
        VK10.vkDestroyPipeline(handle, pipeline.handle(), null);
    }

    public @NotNull VulkanicSwapchain createSwapchain(
            @NotNull EnumIntBitset<VulkanicSwapchainCreateFlag> flags,
            @NotNull VulkanicSurface surface, int minImageCount,
            @NotNull VulkanicFormat imageFormat, @NotNull VulkanicColorSpace imageColorSpace,
            int width, int height, int imageArrayLayers,
            @NotNull EnumIntBitset<VulkanicImageUsageFlag> imageUsage,
            @NotNull VulkanicSharingMode imageSharingMode,
            int queueFamilyIndexCount, int @Nullable [] queueFamilyIndices,
            @NotNull VulkanicSurfaceTransformFlag preTransform, @NotNull VulkanicCompositeAlphaFlag compositeAlpha,
            @NotNull VulkanicPresentMode presentMode, boolean clipped
    ) throws VulkanException {
        return createSwapchain(flags, surface, minImageCount, imageFormat, imageColorSpace, width, height, imageArrayLayers, imageUsage, imageSharingMode, queueFamilyIndexCount, queueFamilyIndices, preTransform, compositeAlpha, presentMode, clipped, null);
    }

    public @NotNull VulkanicSwapchain createSwapchain(
            @NotNull EnumIntBitset<VulkanicSwapchainCreateFlag> flags,
            @NotNull VulkanicSurface surface, int minImageCount,
            @NotNull VulkanicFormat imageFormat, @NotNull VulkanicColorSpace imageColorSpace,
            int width, int height, int imageArrayLayers,
            @NotNull EnumIntBitset<VulkanicImageUsageFlag> imageUsage,
            @NotNull VulkanicSharingMode imageSharingMode,
            int queueFamilyIndexCount, int @Nullable [] queueFamilyIndices,
            @NotNull VulkanicSurfaceTransformFlag preTransform, @NotNull VulkanicCompositeAlphaFlag compositeAlpha,
            @NotNull VulkanicPresentMode presentMode, boolean clipped,
            @Nullable VulkanicSwapchain oldSwapchain
    ) throws VulkanException {
        if (!enabledExtensions.contains(KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME)) throw new UnsupportedOperationException("VulkanicDevice#createSwapchain requires a VK_KHR_swapchain");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pSwapchain = stack.callocLong(1);
            VkSwapchainCreateInfoKHR swapchainCreateInfoKHR = VkSwapchainCreateInfoKHR.calloc(stack)
                    .sType$Default()
                    .flags(flags.mask())
                    .surface(surface.handle())
                    .minImageCount(minImageCount)
                    .imageFormat(imageFormat.qualifier())
                    .imageColorSpace(imageColorSpace.qualifier())
                    .imageExtent(ex -> ex.set(width, height))
                    .imageArrayLayers(imageArrayLayers)
                    .imageUsage(imageUsage.mask())
                    .imageSharingMode(imageSharingMode.qualifier())
                    .queueFamilyIndexCount(queueFamilyIndexCount)
                    .pQueueFamilyIndices(queueFamilyIndices != null ? stack.ints(queueFamilyIndices) : null)
                    .preTransform(preTransform.flag())
                    .compositeAlpha(compositeAlpha.flag())
                    .presentMode(presentMode.qualifier())
                    .clipped(clipped)
                    .oldSwapchain(oldSwapchain != null ? oldSwapchain.handle() : 0);
            VulkanicResult result = VulkanicResult.valueOf(KHRSwapchain.vkCreateSwapchainKHR(this.handle, swapchainCreateInfoKHR, null, pSwapchain));
            if (!result.success()) throw new VulkanException(result);
            return new VulkanicSwapchain(
                    this, pSwapchain.get(0),
                    imageFormat, new Vector3i(width, height, 1), imageUsage
            );
        }
    }

    public void destroySwapchain(@NotNull VulkanicSwapchain swapchain) {
        KHRSwapchain.vkDestroySwapchainKHR(this.handle, swapchain.handle(), null);
    }

    public @NotNull VulkanicSampler createSampler(VulkanicSamplerCreateInfo createInfo) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkSamplerCreateInfo samplerCreateInfo = VkSamplerCreateInfo.calloc(stack)
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
            VkUtil.check(VK10.vkCreateSampler(this.handle, samplerCreateInfo, null, pSampler));
            return new VulkanicSampler(this, pSampler.get(0));
        }
    }

    public void destroySampler(@NotNull VulkanicSampler sampler) {
        VK10.vkDestroySampler(this.handle, sampler.handle(), null);
    }

    public @NotNull VulkanicQueryPool createQueryPool(EnumIntBitset<VulkanicQueryPoolCreateFlag> flags, VulkanicQueryType type, int queryCount, EnumIntBitset<VulkanicQueryPipelineStatisticFlag> pipelineStatistics) throws VulkanException {
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

    public void destroyQueryPool(@NotNull VulkanicQueryPool pool) {
        VK10.vkDestroyQueryPool(this.handle, pool.handle(), null);
    }

    public @NotNull VulkanicResult getQueryPoolResults(@NotNull VulkanicQueryPool queryPool, int firstQuery, int queryCount, @NotNull ByteBuffer pData, long stride, @NotNull EnumIntBitset<VulkanicQueryResultFlag> flags) {
        return VulkanicResult.valueOf(VK10.vkGetQueryPoolResults(this.handle, queryPool.handle(), firstQuery, queryCount, pData, stride, flags.mask()));
    }

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
                    case VulkanicResourceDescriptorData.TensorViewCreateInfo _ -> pData.pTensorARM(VkTensorViewCreateInfoARM.calloc(stack).sType$Default()); // TODO
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

    @SuppressWarnings("resource")
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

    public @NotNull VulkanicDeviceSize getPhysicalDeviceDescriptorSize(VulkanicDescriptorType type) {
        return VulkanicDeviceSize.ofBytes(EXTDescriptorHeap.vkGetPhysicalDeviceDescriptorSizeEXT(this.physicalDevice.handle(), type.qualifier()));
    }

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

    public void destroyPipelineCache(@NotNull VulkanicPipelineCache cache) {
        VK10.vkDestroyPipelineCache(this.handle, cache.handle(), null);
    }

    public @NotNull VulkanicResult mergePipelineCaches(@NotNull VulkanicPipelineCache dstCache, @NotNull List<VulkanicPipelineCache> srcCaches) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            return VulkanicResult.valueOf(VK10.vkMergePipelineCaches(this.handle, dstCache.handle(), stack.longs(srcCaches.stream().mapToLong(VulkanicPipelineCache::handle).toArray())));
        }
    }

    public long getPipelineCacheDataSize(@NotNull VulkanicPipelineCache cache) throws VulkanException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer pDataSize = stack.callocPointer(1);
            VkUtil.check(VK10.vkGetPipelineCacheData(this.handle, cache.handle(), pDataSize, null));
            return pDataSize.get(0);
        }
    }

    //
    @Contract(mutates = "param2", value = "_, _ -> _")
    public @NotNull VulkanicResult getPipelineCacheData(@NotNull VulkanicPipelineCache cache, @NotNull ByteBuffer buffer) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer pDataSize = stack.pointers(buffer.remaining());
            return VulkanicResult.valueOf(VK10.vkGetPipelineCacheData(this.handle, cache.handle(), pDataSize, buffer));
        }
    }

    // TODO: deferred operation
    public @NotNull VulkanicRayTracingPipeline createRayTracingPipeline(
            @NotNull VulkanicRayTracingPipelineCreateInfo createInfo,
            @Nullable VulkanicPipelineCache cache
    ) throws VulkanException {
        createInfo.validate(features);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pOut = stack.callocLong(1);
            VkRayTracingPipelineCreateInfoKHR.Buffer pCreateInfos = VkRayTracingPipelineCreateInfoKHR.calloc(1, stack)
                    .apply(struct -> createInfo.build(struct, stack));

            VkUtil.check(KHRRayTracingPipeline.vkCreateRayTracingPipelinesKHR(this.handle, 0, cache != null ? cache.handle() : 0, pCreateInfos, null, pOut));
            return new VulkanicRayTracingPipeline(this, pOut.get(0));
        }
    }

    public @NotNull CompletableFuture<Void> submitTransient(
            @NotNull VulkanicCommandPool pool,
            @NotNull VulkanicQueue queue,
            @NotNull Consumer<VulkanicCommandBuffer> usage
    ) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        VulkanicCommandBuffer commandBuffer;
        try {
            commandBuffer = allocateCommandBuffer(pool, VulkanicCommandBufferLevel.PRIMARY);
        } catch (VulkanException e) {
            future.completeExceptionally(e);
            return future;
        }
        VulkanicFence fence = null;
        try {
            VulkanicResult result = commandBuffer.begin(EnumIntBitset.of(VulkanicCommandBufferUsageFlag.ONE_TIME_SUBMIT));
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

            VulkanicFence submittedFence = fence;

            Thread.ofVirtual().start(() -> {
                try {
                    submittedFence.waitFor(VkUtil.FOREVER);
                    future.complete(null);
                } catch (Exception e) {
                    future.completeExceptionally(e);
                } finally {
                    commandBuffer.close();
                    submittedFence.close();
                }
            });

            return future;
        } catch (Exception e) {
            commandBuffer.close();

            if (fence != null) {
                fence.close();
            }

            future.completeExceptionally(e);
            return future;
        }
    }

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
}
