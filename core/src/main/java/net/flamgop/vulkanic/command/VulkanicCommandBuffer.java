package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import net.flamgop.vulkanic.core.VulkanicStridedDeviceAddressRegion;
import net.flamgop.vulkanic.exception.VulkanicResult;
import net.flamgop.vulkanic.memory.VulkanicDeviceSize;
import net.flamgop.vulkanic.memory.VulkanicIndexType;
import net.flamgop.vulkanic.memory.copy.VulkanicBufferImageCopy;
import net.flamgop.vulkanic.memory.image.VulkanicImageSubresourceRange;
import net.flamgop.vulkanic.pipeline.*;
import net.flamgop.vulkanic.memory.VulkanicBuffer;
import net.flamgop.vulkanic.memory.image.VulkanicFilter;
import net.flamgop.vulkanic.memory.image.VulkanicImage;
import net.flamgop.vulkanic.memory.image.VulkanicImageLayout;
import net.flamgop.vulkanic.pipeline.descriptor.VulkanicDescriptorSet;
import net.flamgop.vulkanic.pipeline.descriptor.heap.VulkanicHeapBindInfo;
import net.flamgop.vulkanic.pipeline.descriptor.heap.VulkanicPushDataInfo;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicBlendConstants;
import net.flamgop.vulkanic.math.VulkanicRect2D;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicStencilFaceFlag;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicViewport;
import net.flamgop.vulkanic.sync.VulkanicQueryControlFlag;
import net.flamgop.vulkanic.sync.VulkanicQueryPool;
import net.flamgop.vulkanic.sync.VulkanicQueryResultFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;
import net.flamgop.vulkanic.util.EnumLongBitset;
import net.flamgop.vulkanic.util.MathHelper;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.*;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.vulkan.VK13.*;

@SuppressWarnings("UnusedReturnValue")
public final class VulkanicCommandBuffer implements AutoCloseable, VulkanicObject.Typed<VkCommandBuffer> {

    private final VulkanicDevice device;
    private final VulkanicCommandPool pool;
    private final VkCommandBuffer handle;
    private final VulkanicCommandBufferLevel level;

    /// @see VulkanicCommandPool#allocateCommandBuffer
    /// @see VulkanicCommandPool#allocateCommandBuffers
    /// @see VulkanicDevice#allocateCommandBuffer
    /// @see VulkanicDevice#allocateCommandBuffers
    @ApiStatus.Internal
    public VulkanicCommandBuffer(@NotNull VulkanicCommandPool pool, @NotNull VkCommandBuffer handle, @NotNull VulkanicCommandBufferLevel level) {
        this.device = pool.device();
        this.pool = pool;
        this.handle = handle;
        this.level = level;
    }

    /// Begins this command buffer, changing its state from INITIAL to RECORDING
    /// @see VulkanicCommandBufferUsageFlag
    /// @see VulkanicCommandBufferInheritanceInfo
    @Contract(mutates = "this", value = "_ -> !null")
    public @NotNull VulkanicResult begin(@NotNull VulkanicCommandBufferBeginInfo beginInfo) {
        return pool.beginCommandBuffer(this, beginInfo);
    }

    /// Resets this command buffer, changing its state back to INITIAL (from any state other than PENDING or INITIAL)
    /// The flag [VulkanicCommandBufferResetFlag#RESET_RELEASE_RESOURCES] will tell the driver to release associated resources, ideal for use with [VulkanicCommandBufferUsageFlag#ONE_TIME_SUBMIT]
    @Contract(mutates = "this", value = "_ -> !null")
    public @NotNull VulkanicResult reset(@NotNull EnumIntBitset<VulkanicCommandBufferResetFlag> flags) {
        return pool.resetCommandBuffer(this, flags);
    }

    /// Ends this command buffer, changing its state from RECORDING to EXECUTABLE
    @Contract(mutates = "this", value = "-> !null")
    public @NotNull VulkanicResult end() {
        return pool.endCommandBuffer(this);
    }

    /// Binds a pipeline to the given pipeline bind point
    @Contract(mutates = "this")
    public void bindPipeline(@NotNull VulkanicPipelineBindPoint pipelineBindPoint, @NotNull VulkanicPipeline pipeline) {
        vkCmdBindPipeline(handle, pipelineBindPoint.qualifier(), pipeline.handle());
    }

    /// Binds a descriptor set to the given pipeline bind point
    @Contract(mutates = "this")
    public void bindDescriptorSet(@NotNull VulkanicPipelineBindPoint pipelineBindPoint, @NotNull VulkanicPipelineLayout layout, int firstSet, @NotNull VulkanicDescriptorSet set, int @Nullable [] dynamicOffsets) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            vkCmdBindDescriptorSets(handle, pipelineBindPoint.qualifier(), layout.handle(), firstSet, stack.longs(set.handle()), dynamicOffsets != null ? stack.ints(dynamicOffsets) : null);
        }
    }

    /// Binds several descriptor sets to a given pipeline bind point
    @Contract(mutates = "this")
    public void bindDescriptorSets(@NotNull VulkanicPipelineBindPoint pipelineBindPoint, @NotNull VulkanicPipelineLayout layout, int firstSet, @NotNull List<VulkanicDescriptorSet> sets, int @Nullable [] dynamicOffsets) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pSets = stack.callocLong(sets.size());
            for (VulkanicDescriptorSet set : sets) {
                pSets.put(set.handle());
            }
            vkCmdBindDescriptorSets(handle, pipelineBindPoint.qualifier(), layout.handle(), firstSet, pSets, dynamicOffsets != null ? stack.ints(dynamicOffsets) : null);
        }
    }

    /// Changes the push constant values for the next pipeline invocation, provided that the pipeline is compatible with the given pipeline layout.
    @Contract(mutates = "this")
    public void pushConstants(@NotNull VulkanicPipelineLayout layout, @NotNull EnumIntBitset<VulkanicShaderStage> stageFlags, int offset, @NotNull ByteBuffer values) {
        vkCmdPushConstants(handle, layout.handle(), stageFlags.mask(), offset, values);
    }

    /// Dispatches compute workgroups using the pipeline bound to [VulkanicPipelineBindPoint#COMPUTE]
    @Contract(mutates = "this")
    public void dispatch(int groupCountX, int groupCountY, int groupCountZ) {
        vkCmdDispatch(handle, groupCountX, groupCountY, groupCountZ);
    }

    /// Dispatches compute workgroups according to the commands stored in `buffer` using the pipeline bound to [VulkanicPipelineBindPoint#COMPUTE] </p>
    /// Each command in the buffer should be of the format:
    /// ```
    /// struct VkDispatchIndirectCommand {
    ///     uint32_t x; // # of local workgroups to dispatch in the X dimension
    ///     uint32_t y; // # of local workgroups to dispatch in the Y dimension
    ///     uint32_t z; // # of local workgroups to dispatch in the Z dimension
    /// }
    /// ```
    @Contract(mutates = "this")
    public void dispatchIndirect(@NotNull VulkanicBuffer buffer, long offset) {
        vkCmdDispatchIndirect(handle, buffer.handle(), offset);
    }

    /// Sets the viewport(s) for the next (graphics related) pipeline invocations
    /// The bound pipeline must have [net.flamgop.vulkanic.pipeline.graphics.VulkanicDynamicState#VIEWPORT] set in its dynamic state.
    @Contract(mutates = "this")
    public void setViewport(int firstViewport, @NotNull VulkanicViewport @NotNull ... viewports) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkViewport.Buffer pViewports = VkViewport.calloc(viewports.length, stack);
            for (int i = 0; i < viewports.length; i++) {
                viewports[i].get(pViewports.get(i));
            }
            vkCmdSetViewport(handle, firstViewport, pViewports);
        }
    }

    /// Sets the viewport(s) for the next (graphics related) pipeline invocations
    /// The bound pipeline must have [net.flamgop.vulkanic.pipeline.graphics.VulkanicDynamicState#VIEWPORT_WITH_COUNT] set in its dynamic state.
    @Contract(mutates = "this")
    public void setViewportWithCount(@NotNull VulkanicViewport @NotNull ... viewports) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkViewport.Buffer pViewports = VkViewport.calloc(viewports.length, stack);
            for (int i = 0; i < viewports.length; i++) {
                viewports[i].get(pViewports.get(i));
            }
            vkCmdSetViewportWithCount(handle, pViewports);
        }
    }

    /// Sets the scissor(s) for the next (graphics related) pipeline invocations
    /// The bound pipeline must have [net.flamgop.vulkanic.pipeline.graphics.VulkanicDynamicState#SCISSOR] set in its dynamic state.
    @Contract(mutates = "this")
    public void setScissor(int firstScissor, @NotNull VulkanicRect2D @NotNull ... scissors) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkRect2D.Buffer pScissors = VkRect2D.calloc(scissors.length, stack);
            for (int i = 0; i < scissors.length; i++) {
                scissors[i].get(pScissors.get(i));
            }
            vkCmdSetScissor(handle, firstScissor, pScissors);
        }
    }

    /// Sets the scissor(s) for the next (graphics related) pipeline invocations
    /// The bound pipeline must have [net.flamgop.vulkanic.pipeline.graphics.VulkanicDynamicState#SCISSOR_WITH_COUNT] set in its dynamic state.
    @Contract(mutates = "this")
    public void setScissorWithCount(@NotNull VulkanicRect2D @NotNull ... scissors) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkRect2D.Buffer pScissors = VkRect2D.calloc(scissors.length, stack);
            for (int i = 0; i < scissors.length; i++) {
                scissors[i].get(pScissors.get(i));
            }
            vkCmdSetScissorWithCount(handle, pScissors);
        }
    }

    /// Sets the line width for the next (graphics related) pipeline invocations
    /// The bound pipeline must have [net.flamgop.vulkanic.pipeline.graphics.VulkanicDynamicState#LINE_WIDTH] set in its dynamic state.
    @Contract(mutates = "this")
    public void setLineWidth(float lineWidth) {
        vkCmdSetLineWidth(handle, lineWidth);
    }

    /// Sets the depth bias for the next (graphics related) pipeline invocations
    /// The bound pipeline must have [net.flamgop.vulkanic.pipeline.graphics.VulkanicDynamicState#DEPTH_BIAS] set in its dynamic state.
    @Contract(mutates = "this")
    public void setDepthBias(float depthBiasConstantFactor, float depthBiasClamp, float depthBiasSlopeFactor) {
        vkCmdSetDepthBias(handle, depthBiasConstantFactor, depthBiasClamp, depthBiasSlopeFactor);
    }

    /// Sets the blend constants for the next (graphics related) pipeline invocations
    /// The bound pipeline must have [net.flamgop.vulkanic.pipeline.graphics.VulkanicDynamicState#BLEND_CONSTANTS] set in its dynamic state.
    @Contract(mutates = "this")
    public void setBlendConstants(@NotNull VulkanicBlendConstants blendConstants) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            vkCmdSetBlendConstants(handle, stack.floats(blendConstants.r(), blendConstants.g(), blendConstants.b(), blendConstants.a()));
        }
    }

    /// Sets the depth bounds for the next (graphics related) pipeline invocations
    /// The bound pipeline must have [net.flamgop.vulkanic.pipeline.graphics.VulkanicDynamicState#DEPTH_BOUNDS] set in its dynamic state.
    @Contract(mutates = "this")
    public void setDepthBounds(float minDepthBounds, float maxDepthBounds) {
        vkCmdSetDepthBounds(handle, minDepthBounds, maxDepthBounds);
    }

    /// Sets the stencil compare mask for the next (graphics related) pipeline invocations
    /// The bound pipeline must have [net.flamgop.vulkanic.pipeline.graphics.VulkanicDynamicState#STENCIL_COMPARE_MASK] set in its dynamic state.
    @Contract(mutates = "this")
    public void setStencilCompareMask(@NotNull EnumIntBitset<VulkanicStencilFaceFlag> faces, int compareMask) {
        vkCmdSetStencilCompareMask(handle, faces.mask(), compareMask);
    }

    /// Sets the stencil write mask for the next (graphics related) pipeline invocations
    /// The bound pipeline must have [net.flamgop.vulkanic.pipeline.graphics.VulkanicDynamicState#STENCIL_WRITE_MASK] set in its dynamic state.
    @Contract(mutates = "this")
    public void setStencilWriteMask(@NotNull EnumIntBitset<VulkanicStencilFaceFlag> faces, int writeMask) {
        vkCmdSetStencilWriteMask(handle, faces.mask(), writeMask);
    }

    /// Sets the stencil reference for the next (graphics related) pipeline invocations
    /// The bound pipeline must have [net.flamgop.vulkanic.pipeline.graphics.VulkanicDynamicState#STENCIL_REFERENCE] set in its dynamic state.
    @Contract(mutates = "this")
    public void setStencilReference(@NotNull EnumIntBitset<VulkanicStencilFaceFlag> faces, int reference) {
        vkCmdSetStencilReference(handle, faces.mask(), reference);
    }

    /// Binds several vertex buffers for the next (graphics related) pipeline invocations
    @Contract(mutates = "this")
    public void bindVertexBuffers(int firstBinding, @NotNull VulkanicBuffer @NotNull [] buffers, long @NotNull [] offsets) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pBuffers = stack.longs(Arrays.stream(buffers).mapToLong(VulkanicBuffer::handle).toArray());
            LongBuffer pOffsets = stack.longs(offsets);
            vkCmdBindVertexBuffers(handle, firstBinding, pBuffers, pOffsets);
        }
    }

    /// Binds a single vertex buffer for the next (graphics related) pipeline invocations (this effectively just a wrapper for [#bindVertexBuffers] )
    @Contract(mutates = "this")
    public void bindVertexBuffer(int firstBinding, @NotNull VulkanicBuffer buffer, long offset) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pBuffers = stack.longs(buffer.handle());
            LongBuffer pOffsets = stack.longs(offset);
            vkCmdBindVertexBuffers(handle, firstBinding, pBuffers, pOffsets);
        }
    }

    /// Binds an index buffer for the next (graphics related) pipeline invocations, to index the vertex buffer by.
    @Contract(mutates = "this")
    public void bindIndexBuffer(@NotNull VulkanicBuffer buffer, long offset, @NotNull VulkanicIndexType indexType) {
        vkCmdBindIndexBuffer(handle, buffer.handle(), offset, indexType.qualifier());
    }

    /// Executes the bound graphics pipeline with the bound vertex buffer(s), invoking its vertex shader and, if present, its geometry, tesselation, and fragment shaders.
    /// The vertex shader is invoked `instanceCount` times for each vertex provided by `vertexCount`.
    @Contract(mutates = "this")
    public void draw(int vertexCount, int instanceCount, int firstVertex, int firstInstance) {
        vkCmdDraw(handle, vertexCount, instanceCount, firstVertex, firstInstance);
    }

    /// Executes the bound graphics pipeline with the bound vertex buffer(s), indexing them by the bound index buffer, invoking its vertex shader and, if present, its geometry, tesselation, and fragment shaders.
    /// The vertex shader is invoked `instanceCount` times for each index provided by `indexCount`.
    @Contract(mutates = "this")
    public void drawIndexed(int indexCount, int instanceCount, int firstIndex, int vertexOffset, int firstInstance) {
        vkCmdDrawIndexed(handle, indexCount, instanceCount, firstIndex, vertexOffset, firstInstance);
    }

    /// Executes the bound graphics pipeline with the bound vertex buffer(s), invoking its vertex shader and, if present, its geometry, tesselation, and fragment shaders.
    /// This invokes the vertex shader according to the information in the given indirect buffer, which consists of a flat list of:
    /// ```
    /// struct VkDrawIndirectCommand {
    ///     uint32_t vertexCount;
    ///     uint32_t instanceCount;
    ///     uint32_t firstVertex;
    ///     uint32_t firstInstance;
    ///}
    /// ```
    @Contract(mutates = "this")
    public void drawIndirect(@NotNull VulkanicBuffer buffer, long offset, int drawCount, int stride) {
        vkCmdDrawIndirect(handle, buffer.handle(), offset, drawCount, stride);
    }

    /// Executes the bound graphics pipeline with the bound vertex buffer(s), indexing them by the bound index buffer, invoking its vertex shader and, if present, its geometry, tesselation, and fragment shaders.
    /// This invokes the vertex shader according to the information in the given indirect buffer, which consists of a flat list of:
    /// ```
    /// struct VkDrawIndexedIndirectCommand  {
    ///     uint32_t indexCount;
    ///     uint32_t instanceCount;
    ///     uint32_t firstIndex;
    ///     uint32_t vertexOffset;
    ///     uint32_t firstInstance;
    ///}
    /// ```
    @Contract(mutates = "this")
    public void drawIndexedIndirect(@NotNull VulkanicBuffer buffer, long offset, int drawCount, int stride) {
        vkCmdDrawIndexedIndirect(handle, buffer.handle(), offset, drawCount, stride);
    }

    /// Blits an image from `srcImage` with `srcLayout` to `dstImage` with `dstLayout` according to `pRegions` and scaling with `filter`
    @Contract(mutates = "this")
    public void blitImage(@NotNull VulkanicImage srcImage, @NotNull VulkanicImageLayout srcLayout, @NotNull VulkanicImage dstImage, @NotNull VulkanicImageLayout dstLayout, @NotNull VkImageBlit.Buffer pRegions, @NotNull VulkanicFilter filter) {
        vkCmdBlitImage(handle, srcImage.handle(), srcLayout.qualifier(), dstImage.handle(), dstLayout.qualifier(), pRegions, filter.qualifier());
    }

    /// Clears the current depth-stencil image as provided by the currently bound render pass or [#beginRendering]`
    @Contract(mutates = "this")
    public void clearDepthStencilImage(@NotNull VulkanicImage image, @NotNull VulkanicImageLayout layout, @NotNull VkClearDepthStencilValue pDepthStencil, @NotNull VkImageSubresourceRange.Buffer pRanges) {
        vkCmdClearDepthStencilImage(handle, image.handle(), layout.qualifier(), pDepthStencil, pRanges);
    }

    /// Clears the current attachments as provided by the currently bound render pass or [#beginRendering]
    @SuppressWarnings("resource")
    @Contract(mutates = "this")
    public void clearAttachments(@NotNull List<VulkanicClearAttachment> attachments, @NotNull List<VulkanicClearRect> rects) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkClearAttachment.Buffer pAttachments = VkClearAttachment.calloc(attachments.size(), stack);
            for (int i = 0; i < attachments.size(); i++) {
                VulkanicClearAttachment attachment = attachments.get(i);
                pAttachments.get(i)
                        .aspectMask(attachment.aspectMask().mask())
                        .colorAttachment(attachment.colorAttachment())
                        .clearValue(v -> attachment.clearValue().copyTo(v));
            }

            VkClearRect.Buffer pRects = VkClearRect.calloc(rects.size(), stack);
            for (int i = 0; i < rects.size(); i++) {
                VulkanicClearRect rect = rects.get(i);
                pRects.get(i)
                        .rect(r -> rect.rect().get(r))
                        .baseArrayLayer(rect.baseArrayLayer())
                        .layerCount(rect.layerCount());
            }
            
            vkCmdClearAttachments(handle, pAttachments, pRects);
        }
    }

    /// Resolves an image from `srcImage` with `srcLayout` to `dstImage` with `dstLayout` according to `pRegions`
    @Contract(mutates = "this")
    public void resolveImage(@NotNull VulkanicImage srcImage, @NotNull VulkanicImageLayout srcLayout, @NotNull VulkanicImage dstImage, @NotNull VulkanicImageLayout dstLayout, @NotNull VkImageResolve.Buffer pRegions) {
        vkCmdResolveImage(handle, srcImage.handle(), srcLayout.qualifier(), dstImage.handle(), dstLayout.qualifier(), pRegions);
    }

    /// Begins a render pass
    @Contract(mutates = "this")
    public void beginRenderPass(@NotNull VkRenderPassBeginInfo pRenderPassBegin, int contents) {
        vkCmdBeginRenderPass(handle, pRenderPassBegin, contents);
    }

    /// Switches to the next subpass
    @Contract(mutates = "this")
    public void nextSubpass(int contents) {
        vkCmdNextSubpass(handle, contents);
    }

    /// Ends a render pass
    @Contract(mutates = "this")
    public void endRenderPass() {
        vkCmdEndRenderPass(handle);
    }

    /// Updates the data in a buffer from a CPU buffer
    @Contract(mutates = "this") /*this technically mutates buffer, but doesn't mutate it until the command is submitted, how do I manage that?*/
    public void updateBuffer(@NotNull VulkanicBuffer buffer, long dstOffset, @NotNull ByteBuffer data) {
        vkCmdUpdateBuffer(handle, buffer.handle(), dstOffset, data);
    }

    /// Copies data between two GPU buffers
    @Contract(mutates = "this")
    public void copyBuffer(@NotNull VulkanicBuffer srcBuffer, @NotNull VulkanicBuffer dstBuffer, VkBufferCopy.Buffer pRegions) {
        vkCmdCopyBuffer(handle, srcBuffer.handle(), dstBuffer.handle(), pRegions);
    }

    /// Copies data between two GPU images
    @Contract(mutates = "this")
    public void copyImage(@NotNull VulkanicImage srcImage, @NotNull VulkanicImageLayout srcLayout, @NotNull VulkanicImage dstImage, @NotNull VulkanicImageLayout dstLayout, @NotNull VkImageCopy.Buffer pRegions) {
        vkCmdCopyImage(handle, srcImage.handle(), srcLayout.qualifier(), dstImage.handle(), dstLayout.qualifier(), pRegions);
    }

    /// Copies a GPU buffer to a GPU image
    @SuppressWarnings("resource")
    @Contract(mutates = "this")
    public void copyBufferToImage(@NotNull VulkanicBuffer srcBuffer, @NotNull VulkanicImage dstImage, @NotNull VulkanicImageLayout dstLayout, @NotNull List<@NotNull VulkanicBufferImageCopy> regions) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkBufferImageCopy.Buffer pRegions = VkBufferImageCopy.calloc(regions.size(), stack);
            for (int i = 0; i < regions.size(); i++) {
                VulkanicBufferImageCopy region = regions.get(i);
                pRegions.get(i)
                        .bufferOffset(region.bufferOffset())
                        .bufferRowLength(region.bufferRowLength())
                        .bufferImageHeight(region.bufferImageHeight())
                        .imageSubresource(r -> r
                                .aspectMask(region.imageSubresource().aspectMask().mask())
                                .mipLevel(region.imageSubresource().mipLevel())
                                .baseArrayLayer(region.imageSubresource().baseArrayLevel())
                                .layerCount(region.imageSubresource().layerCount())
                        )
                        .imageOffset(o -> o.set(region.imageOffset().x(), region.imageOffset().y(), region.imageOffset().z()))
                        .imageExtent(e -> e.set(region.imageExtent().x(), region.imageExtent().y(), region.imageExtent().z()));
            }
            vkCmdCopyBufferToImage(handle, srcBuffer.handle(), dstImage.handle(), dstLayout.qualifier(), pRegions);
        }
    }

    /// Copies a GPU image to a GPU buffer
    @Contract(mutates = "this")
    public void copyImageToBuffer(@NotNull VulkanicImage srcImage, @NotNull VulkanicImageLayout srcLayout, @NotNull VulkanicBuffer dstBuffer, VkBufferImageCopy.Buffer pRegions) {
        vkCmdCopyImageToBuffer(handle, srcImage.handle(), srcLayout.qualifier(), dstBuffer.handle(), pRegions);
    }

    /// Fills a GPU buffer with a single value
    @Contract(mutates = "this")
    public void fillBuffer(@NotNull VulkanicBuffer dstBuffer, long dstOffset, @NotNull VulkanicDeviceSize size, int data) {
        vkCmdFillBuffer(handle, dstBuffer.handle(), dstOffset, size.bytes(), data);
    }

    /// Inserts a pipeline barrier for read and write safety, this is the old function for Vulkan setups without synchronization2.
    /// You probably want to use [#pipelineBarrier(VulkanicDependencyInfo)] if possible.
    @SuppressWarnings("resource")
    @Contract(mutates = "this")
    public void pipelineBarrier(
            @NotNull EnumLongBitset<VulkanicPipelineStageFlag> srcStageMask,
            @NotNull EnumLongBitset<VulkanicPipelineStageFlag> dstStageMask,
            @NotNull EnumIntBitset<VulkanicDependencyFlag> dependencyFlags,
            @NotNull List<VulkanicMemoryBarrier> memoryBarriers,
            @NotNull List<VulkanicBufferMemoryBarrier> bufferMemoryBarriers,
            @NotNull List<VulkanicImageMemoryBarrier> imageMemoryBarriers
    ) {
        if (!MathHelper.fitsInInt(srcStageMask.mask()) || !MathHelper.fitsInInt(dstStageMask.mask())) throw new IllegalArgumentException("VulkanicCommandBuffer#pipelineBarrier's non-synchronization2 overload does not support extended (long precision) stage flags (i.e., any flags that do not fit within an integer, and would thus cause Math#toIntExact to fail), please use VulkanicCommandBuffer#pipelineBarrier(VulkanicDependencyInfo)");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkMemoryBarrier.Buffer pMemoryBarriers = VkMemoryBarrier.calloc(memoryBarriers.size(), stack);
            for (int i = 0; i < memoryBarriers.size(); i++) {
                VulkanicMemoryBarrier memoryBarrier = memoryBarriers.get(i);
                if (memoryBarrier.srcStageMask().some() || memoryBarrier.dstStageMask().some()) throw new IllegalArgumentException("VulkanicCommandBuffer#pipelineBarrier's non-synchronization2 overload does not support per-barrier src and dst stage masks, please use VulkanicCommandBuffer#pipelineBarrier(VulkanicDependencyInfo)");
                if (!MathHelper.fitsInInt(memoryBarrier.srcAccessMask().mask()) || !MathHelper.fitsInInt(memoryBarrier.dstAccessMask().mask())) throw new IllegalArgumentException("VulkanicCommandBuffer#pipelineBarrier's non-synchronization2 overload does not support extended (long precision) access flags (i.e., any flags that do not fit within an integer, and would thus cause Math#toIntExact to fail), please use VulkanicCommandBuffer#pipelineBarrier(VulkanicDependencyInfo)");
                pMemoryBarriers.get(i)
                        .sType$Default()
                        .srcAccessMask(Math.toIntExact(memoryBarrier.srcAccessMask().mask()))
                        .dstAccessMask(Math.toIntExact(memoryBarrier.dstAccessMask().mask()));
            }

            VkBufferMemoryBarrier.Buffer pBufferMemoryBarriers = VkBufferMemoryBarrier.calloc(bufferMemoryBarriers.size(), stack);
            for (int i = 0; i < bufferMemoryBarriers.size(); i++) {
                VulkanicBufferMemoryBarrier bufferBarrier = bufferMemoryBarriers.get(i);
                if (bufferBarrier.srcStageMask().some() || bufferBarrier.dstStageMask().some()) throw new IllegalArgumentException("VulkanicCommandBuffer#pipelineBarrier's non-synchronization2 overload does not support per-barrier src and dst stage masks, please use VulkanicCommandBuffer#pipelineBarrier(VulkanicDependencyInfo)");
                if (!MathHelper.fitsInInt(bufferBarrier.srcAccessMask().mask()) || !MathHelper.fitsInInt(bufferBarrier.dstAccessMask().mask())) throw new IllegalArgumentException("VulkanicCommandBuffer#pipelineBarrier's non-synchronization2 overload does not support extended (long precision) access flags (i.e., any flags that do not fit within an integer, and would thus cause Math#toIntExact to fail), please use VulkanicCommandBuffer#pipelineBarrier(VulkanicDependencyInfo)");
                pBufferMemoryBarriers.get(i)
                        .sType$Default()
                        .srcAccessMask(Math.toIntExact(bufferBarrier.srcAccessMask().mask()))
                        .dstAccessMask(Math.toIntExact(bufferBarrier.dstAccessMask().mask()))
                        .srcQueueFamilyIndex(bufferBarrier.srcQueueFamilyIndex())
                        .dstQueueFamilyIndex(bufferBarrier.dstQueueFamilyIndex())
                        .buffer(bufferBarrier.buffer().handle()).offset(bufferBarrier.offset()).size(bufferBarrier.size());
            }

            VkImageMemoryBarrier.Buffer pImageMemoryBarriers = VkImageMemoryBarrier.calloc(imageMemoryBarriers.size(), stack);
            for (int i = 0; i < imageMemoryBarriers.size(); i++) {
                VulkanicImageMemoryBarrier imageBarrier = imageMemoryBarriers.get(i);
                if (imageBarrier.srcStageMask().some() || imageBarrier.dstStageMask().some()) throw new IllegalArgumentException("VulkanicCommandBuffer#pipelineBarrier's non-synchronization2 overload does not support per-barrier src and dst stage masks, please use VulkanicCommandBuffer#pipelineBarrier(VulkanicDependencyInfo)");
                if (!MathHelper.fitsInInt(imageBarrier.srcAccessMask().mask()) || !MathHelper.fitsInInt(imageBarrier.dstAccessMask().mask())) throw new IllegalArgumentException("VulkanicCommandBuffer#pipelineBarrier's non-synchronization2 overload does not support extended (long precision) access flags (i.e., any flags that do not fit within an integer, and would thus cause Math#toIntExact to fail), please use VulkanicCommandBuffer#pipelineBarrier(VulkanicDependencyInfo)");
                pImageMemoryBarriers.get(i)
                        .sType$Default()
                        .srcAccessMask(Math.toIntExact(imageBarrier.srcAccessMask().mask()))
                        .dstAccessMask(Math.toIntExact(imageBarrier.dstAccessMask().mask()))
                        .oldLayout(imageBarrier.oldLayout().qualifier()).newLayout(imageBarrier.newLayout().qualifier())
                        .srcQueueFamilyIndex(imageBarrier.srcQueueFamilyIndex()).dstQueueFamilyIndex(imageBarrier.dstQueueFamilyIndex())
                        .image(imageBarrier.image().handle()).subresourceRange(imageBarrier.subresourceRange()::get);
            }

            vkCmdPipelineBarrier(handle, Math.toIntExact(srcStageMask.mask()), Math.toIntExact(dstStageMask.mask()), dependencyFlags.mask(), pMemoryBarriers, pBufferMemoryBarriers, pImageMemoryBarriers);
        }
    }

    /// Inserts a pipeline barrier for read and write safety, the preferred method for pipeline barriers.
    /// Requires device synchronization2 feature
    @SuppressWarnings("resource")
    @Contract(mutates = "this")
    public void pipelineBarrier(@NotNull VulkanicDependencyInfo info) {
        if (!device.features().supportsSynchronization2()) {
            throw new UnsupportedOperationException("CommandBuffer#pipelineBarrier(VulkanicDependencyInfo) requires the synchronization2 device extension/feature to be enabled! (Or use the non-synchronization2 method with the same name.)");
        }
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkMemoryBarrier2.Buffer pMemoryBarriers = VkMemoryBarrier2.calloc(info.memoryBarriers().size(), stack);
            for (int i = 0; i < info.memoryBarriers().size(); i++) {
                VulkanicMemoryBarrier memoryBarrier = info.memoryBarriers().get(i);
                pMemoryBarriers.get(i)
                        .sType$Default()
                        .srcStageMask(memoryBarrier.srcStageMask().mask())
                        .srcAccessMask(memoryBarrier.srcAccessMask().mask())
                        .dstStageMask(memoryBarrier.dstStageMask().mask())
                        .dstAccessMask(memoryBarrier.dstAccessMask().mask());
            }

            VkBufferMemoryBarrier2.Buffer pBufferMemoryBarriers = VkBufferMemoryBarrier2.calloc(info.bufferMemoryBarriers().size(), stack);
            for (int i = 0; i < info.bufferMemoryBarriers().size(); i++) {
                VulkanicBufferMemoryBarrier bufferBarrier = info.bufferMemoryBarriers().get(i);
                pBufferMemoryBarriers.get(i)
                        .sType$Default()
                        .srcStageMask(bufferBarrier.srcStageMask().mask())
                        .srcAccessMask(bufferBarrier.srcAccessMask().mask())
                        .dstStageMask(bufferBarrier.dstStageMask().mask())
                        .dstAccessMask(bufferBarrier.dstAccessMask().mask())
                        .srcQueueFamilyIndex(bufferBarrier.srcQueueFamilyIndex())
                        .dstQueueFamilyIndex(bufferBarrier.dstQueueFamilyIndex())
                        .buffer(bufferBarrier.buffer().handle()).offset(bufferBarrier.offset()).size(bufferBarrier.size());
            }

            VkImageMemoryBarrier2.Buffer pImageMemoryBarriers = VkImageMemoryBarrier2.calloc(info.imageMemoryBarriers().size(), stack);
            for (int i = 0; i < info.imageMemoryBarriers().size(); i++) {
                VulkanicImageMemoryBarrier imageBarrier = info.imageMemoryBarriers().get(i);
                pImageMemoryBarriers.get(i)
                        .sType$Default()
                        .srcStageMask(imageBarrier.srcStageMask().mask())
                        .srcAccessMask(imageBarrier.srcAccessMask().mask())
                        .dstStageMask(imageBarrier.dstStageMask().mask())
                        .dstAccessMask(imageBarrier.dstAccessMask().mask())
                        .oldLayout(imageBarrier.oldLayout().qualifier()).newLayout(imageBarrier.newLayout().qualifier())
                        .srcQueueFamilyIndex(imageBarrier.srcQueueFamilyIndex()).dstQueueFamilyIndex(imageBarrier.dstQueueFamilyIndex())
                        .image(imageBarrier.image().handle()).subresourceRange(imageBarrier.subresourceRange()::get);
            }

            VkDependencyInfo pDependencyInfo = VkDependencyInfo.calloc(stack);
            pDependencyInfo
                    .sType$Default()
                    .dependencyFlags(info.dependencyFlags().mask())
                    .pMemoryBarriers(pMemoryBarriers)
                    .pBufferMemoryBarriers(pBufferMemoryBarriers)
                    .pImageMemoryBarriers(pImageMemoryBarriers);

            vkCmdPipelineBarrier2(this.handle, pDependencyInfo);
        }
    }

    /// Executes secondary command buffers in this primary command buffer
    @Contract(mutates = "this")
    public void executeCommands(@NotNull VulkanicCommandBuffer @NotNull ... commandBuffers) {
        if (this.level() != VulkanicCommandBufferLevel.PRIMARY) throw new UnsupportedOperationException("VulkanicCommandBuffer#executeCommands may only be executed on PRIMARY command buffers!");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer pCommandBuffers = stack.callocPointer(commandBuffers.length);
            for (int i = 0; i < commandBuffers.length; i++) {
                if (commandBuffers[i].level() != VulkanicCommandBufferLevel.SECONDARY) throw new UnsupportedOperationException("VulkanicCommandBuffer#executeCommands may only accept SECONDARY command buffers!");
                pCommandBuffers.put(i, commandBuffers[i].handle());
            }

            vkCmdExecuteCommands(handle, pCommandBuffers);
        }
    }

    /// Begins dynamic rendering
    /// Requires VK 1.3 or VK_KHR_dynamic_rendering
    @SuppressWarnings("resource")
    @Contract(mutates = "this")
    public void beginRendering(@NotNull VulkanicRenderingInfo renderingInfo) {
        if (!device.features().supportsDynamicRendering()) {
            throw new UnsupportedOperationException("VulkanicCommandBuffer#beginRendering requires the dynamicRendering device feature.");
        }
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkRenderingAttachmentInfo.Buffer pColorAttachments = VkRenderingAttachmentInfo.calloc(renderingInfo.colorAttachments().size(), stack);
            for (int i = 0; i < renderingInfo.colorAttachments().size(); i++) {
                VulkanicRenderingAttachmentInfo info = renderingInfo.colorAttachments().get(i);
                pColorAttachments.get(i)
                        .sType$Default()
                        .imageView(info.imageView().handle())
                        .imageLayout(info.imageLayout().qualifier())
                        .loadOp(info.loadOp().qualifier())
                        .storeOp(info.storeOp().qualifier())
                        .clearValue(clear -> info.clearValue().copyTo(clear));
                if (info.resolveInfo() != null) {
                    pColorAttachments.get(i)
                            .resolveMode(info.resolveInfo().mode().mask())
                            .resolveImageView(info.resolveInfo().imageView().handle())
                            .resolveImageLayout(info.resolveInfo().imageLayout().qualifier());
                }
            }

            VkRenderingAttachmentInfo pDepthAttachment, pStencilAttachment;
            if (renderingInfo.depthAttachment() != null) {
                VulkanicRenderingAttachmentInfo info = renderingInfo.depthAttachment();
                pDepthAttachment = VkRenderingAttachmentInfo.calloc(stack)
                        .sType$Default()
                        .imageView(info.imageView().handle())
                        .imageLayout(info.imageLayout().qualifier())
                        .loadOp(info.loadOp().qualifier())
                        .storeOp(info.storeOp().qualifier())
                        .clearValue(clear -> info.clearValue().copyTo(clear));
                if (info.resolveInfo() != null) {
                    pDepthAttachment
                            .resolveMode(info.resolveInfo().mode().mask())
                            .resolveImageView(info.resolveInfo().imageView().handle())
                            .resolveImageLayout(info.resolveInfo().imageLayout().qualifier());
                }
            } else pDepthAttachment = null;
            if (renderingInfo.stencilAttachment() != null) {
                VulkanicRenderingAttachmentInfo info = renderingInfo.stencilAttachment();
                pStencilAttachment = VkRenderingAttachmentInfo.calloc(stack)
                        .sType$Default()
                        .imageView(info.imageView().handle())
                        .imageLayout(info.imageLayout().qualifier())
                        .loadOp(info.loadOp().qualifier())
                        .storeOp(info.storeOp().qualifier())
                        .clearValue(clear -> info.clearValue().copyTo(clear));
                if (info.resolveInfo() != null) {
                    pStencilAttachment
                            .resolveMode(info.resolveInfo().mode().mask())
                            .resolveImageView(info.resolveInfo().imageView().handle())
                            .resolveImageLayout(info.resolveInfo().imageLayout().qualifier());
                }
            } else pStencilAttachment = null;

            VkRenderingInfo pRenderingInfo = VkRenderingInfo.calloc(stack)
                    .sType$Default()
                    .flags(renderingInfo.flags().mask())
                    .renderArea(r -> renderingInfo.renderArea().get(r))
                    .layerCount(renderingInfo.layerCount()).viewMask(renderingInfo.viewMask())
                    .pColorAttachments(pColorAttachments)
                    .pDepthAttachment(pDepthAttachment)
                    .pStencilAttachment(pStencilAttachment);
            vkCmdBeginRendering(handle, pRenderingInfo);
        }
    }

    /// Ends dynamic rendering
    @Contract(mutates = "this")
    public void endRendering() {
        if (!device.features().supportsDynamicRendering()) {
            throw new UnsupportedOperationException("VulkanicCommandBuffer#endRendering requires the dynamicRendering device feature.");
        }
        vkCmdEndRendering(handle);
    }

    /// Invokes the task shader of the bound graphics pipeline according to the local workgroup counts provided.
    @Contract(mutates = "this")
    public void drawMeshTasksEXT(int groupCountX, int groupCountY, int groupCountZ) {
        if (!device.features().supportsMeshShader()) {
            throw new UnsupportedOperationException("VulkanicCommandBuffer#drawMeshTasksEXT requires the meshShader device feature.");
        }
        EXTMeshShader.vkCmdDrawMeshTasksEXT(this.handle, groupCountX, groupCountY, groupCountZ);
    }

    /// Invokes the task shader of the bound graphics pipeline according to the local workgroup counts provided in the provided indirect buffer
    /// ```
    /// struct VkDrawMeshTasksIndirectCommandEXT {
    ///     uint32_t groupCountX;
    ///     uint32_t groupCountY;
    ///     uint32_t groupCountZ;
    /// }
    /// ```
    @Contract(mutates = "this")
    public void drawMeshTasksIndirectEXT(@NotNull VulkanicBuffer buffer, @NotNull VulkanicDeviceSize offset, int drawCount, int stride) {
        if (!device.features().supportsMeshShader()) {
            throw new UnsupportedOperationException("VulkanicCommandBuffer#drawMeshTasksIndirectEXT requires the meshShader device feature.");
        }
        EXTMeshShader.vkCmdDrawMeshTasksIndirectEXT(this.handle, buffer.handle(), offset.bytes(), drawCount, stride);
    }

    /// Invokes the task shader of the bound graphics pipeline according to the local workgroup counts provided in the provided indirect buffer, with a drawCount specified by the countBuffer
    /// See [#drawMeshTasksIndirectEXT] for the format of the indirect buffer.
    @Contract(mutates = "this")
    public void drawMeshTasksIndirectCountEXT(@NotNull VulkanicBuffer buffer, @NotNull VulkanicDeviceSize offset, @NotNull VulkanicBuffer countBuffer, @NotNull VulkanicDeviceSize countBufferOffset, int maxDrawCount, int stride) {
        if (!device.features().supportsMeshShader()) {
            throw new UnsupportedOperationException("VulkanicCommandBuffer#drawMeshTasksIndirectCountEXT requires the meshShader device feature.");
        }
        EXTMeshShader.vkCmdDrawMeshTasksIndirectCountEXT(this.handle, buffer.handle(), offset.bytes(), countBuffer.handle(), countBufferOffset.bytes(), maxDrawCount, stride);
    }

    /// Transitions the layout of an image by blocking RW on all commands
    /// This is a helper, not designed to be the most performant solution. See the other overloads for more granular control.
    /// Requires synchronization2
    @Contract(mutates = "this")
    public void transitionImageLayout(
            @NotNull VulkanicImage image,
            @NotNull VulkanicImageLayout oldLayout,
            @NotNull VulkanicImageLayout newLayout
    ) {
        transitionImageLayout(image, oldLayout, newLayout,
                EnumLongBitset.of(VulkanicPipelineStageFlag.ALL_COMMANDS),
                EnumLongBitset.of(VulkanicAccessFlag.MEMORY_READ, VulkanicAccessFlag.MEMORY_WRITE),
                EnumLongBitset.of(VulkanicPipelineStageFlag.ALL_COMMANDS),
                EnumLongBitset.of(VulkanicAccessFlag.MEMORY_READ, VulkanicAccessFlag.MEMORY_WRITE)
        );
    }

    /// Transitions the layout of an image on specific mip levels by blocking RW on all commands
    /// This is a helper, not designed to be the most performant solution. See the other overloads for more granular control.
    /// Requires synchronization2
    @Contract(mutates = "this")
    public void transitionImageLayout(
            @NotNull VulkanicImage image,
            @NotNull VulkanicImageLayout oldLayout,
            @NotNull VulkanicImageLayout newLayout,
            int baseMipLevel, int numMipLevels
    ) {
        transitionImageLayout(image, oldLayout, newLayout,
                EnumLongBitset.of(VulkanicPipelineStageFlag.ALL_COMMANDS),
                EnumLongBitset.of(VulkanicAccessFlag.MEMORY_READ, VulkanicAccessFlag.MEMORY_WRITE),
                EnumLongBitset.of(VulkanicPipelineStageFlag.ALL_COMMANDS),
                EnumLongBitset.of(VulkanicAccessFlag.MEMORY_READ, VulkanicAccessFlag.MEMORY_WRITE),
                baseMipLevel, numMipLevels
        );
    }

    /// Transitions the layout of an image with more granular barrier controls
    /// This is safe to use if you only need to transition a single image. For multiple, prefer building your own pipeline barrier setup.
    /// Requires synchronization2
    @Contract(mutates = "this")
    public void transitionImageLayout(
            @NotNull VulkanicImage image,
            @NotNull VulkanicImageLayout oldLayout,
            @NotNull VulkanicImageLayout newLayout,
            @NotNull EnumLongBitset<VulkanicPipelineStageFlag> srcStage,
            @NotNull EnumLongBitset<VulkanicAccessFlag> srcAccessMask,
            @NotNull EnumLongBitset<VulkanicPipelineStageFlag> dstStage,
            @NotNull EnumLongBitset<VulkanicAccessFlag> dstAccessMask
    ) {
        transitionImageLayout(image, oldLayout, newLayout, srcStage, srcAccessMask, dstStage, dstAccessMask, 0, -1);
    }

    /// Gives full control over the transition of a single image's layout. For multiple images, prefer manual pipeline barriers via [#pipelineBarrier]
    /// Requires synchronization2
    @Contract(mutates = "this")
    public void transitionImageLayout(
            @NotNull VulkanicImage image,
            @NotNull VulkanicImageLayout oldLayout,
            @NotNull VulkanicImageLayout newLayout,
            @NotNull EnumLongBitset<VulkanicPipelineStageFlag> srcStage,
            @NotNull EnumLongBitset<VulkanicAccessFlag> srcAccessMask,
            @NotNull EnumLongBitset<VulkanicPipelineStageFlag> dstStage,
            @NotNull EnumLongBitset<VulkanicAccessFlag> dstAccessMask,
            int baseMipLevel, int numMipLevels
    ) {
        pipelineBarrier(new VulkanicDependencyInfo(
                EnumIntBitset.of(),
                List.of(),
                List.of(),
                List.of(
                        new VulkanicImageMemoryBarrier(
                                srcStage,
                                srcAccessMask,
                                dstStage,
                                dstAccessMask,
                                oldLayout, newLayout,
                                VK_QUEUE_FAMILY_IGNORED, VK_QUEUE_FAMILY_IGNORED,
                                image, new VulkanicImageSubresourceRange(image.aspectMask(), baseMipLevel, numMipLevels, 0, VK_REMAINING_ARRAY_LAYERS)
                        )
                )
        ));
    }

    /// Begins a query
    @Contract(mutates = "this")
    public void beginQuery(@NotNull VulkanicQueryPool queryPool, int query, @NotNull EnumIntBitset<VulkanicQueryControlFlag> flags) {
        vkCmdBeginQuery(this.handle, queryPool.handle(), query, flags.mask());
    }

    /// Ends a query
    @Contract(mutates = "this")
    public void endQuery(@NotNull VulkanicQueryPool queryPool, int query) {
        vkCmdEndQuery(this.handle, queryPool.handle(), query);
    }

    /// Resets a query pool
    @Contract(mutates = "this")
    public void resetQueryPool(@NotNull VulkanicQueryPool queryPool, int firstQuery, int queryCount) {
        vkCmdResetQueryPool(this.handle, queryPool.handle(), firstQuery, queryCount);
    }

    /// Writes the timestamp to a query
    @Contract(mutates = "this")
    public void writeTimestamp(@NotNull EnumLongBitset<VulkanicPipelineStageFlag> pipelineStage, @NotNull VulkanicQueryPool queryPool, int query) {
        vkCmdWriteTimestamp2(this.handle, pipelineStage.mask(), queryPool.handle(), query);
    }

    /// Copies the query pool results into a GPU buffer
    @Contract(mutates = "this")
    public void copyQueryPoolResults(@NotNull VulkanicQueryPool queryPool, int firstQuery, int queryCount, @NotNull VulkanicBuffer dstBuffer, long dstOffset, long stride, @NotNull EnumIntBitset<VulkanicQueryResultFlag> flags) {
        vkCmdCopyQueryPoolResults(this.handle, queryPool.handle(), firstQuery, queryCount, dstBuffer.handle(), dstOffset, stride, flags.mask());
    }

    /// Binds a resource heap
    /// Requires the descriptor heap feature
    @Contract(mutates = "this")
    public void bindResourceHeap(@NotNull VulkanicHeapBindInfo bindInfo) {
        if (!device.features().supportsDescriptorHeap()) throw new UnsupportedOperationException("VulkanicCommandBuffer#bindResourceHeap requires the descriptor heap feature");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkBindHeapInfoEXT pBindInfo = VkBindHeapInfoEXT.calloc(stack)
                    .sType$Default()
                    .heapRange(r -> r.address$(bindInfo.heapRange().address()).size(bindInfo.heapRange().size().bytes()))
                    .reservedRangeOffset(bindInfo.reservedRangeOffset().bytes())
                    .reservedRangeSize(bindInfo.reservedRangeSize().bytes());
            EXTDescriptorHeap.vkCmdBindResourceHeapEXT(this.handle, pBindInfo);
        }
    }

    /// Binds a sampler heap
    /// Requires the descriptor heap feature
    @Contract(mutates = "this")
    public void bindSamplerHeap(@NotNull VulkanicHeapBindInfo bindInfo) {
        if (!device.features().supportsDescriptorHeap()) throw new UnsupportedOperationException("VulkanicCommandBuffer#bindSamplerHeap requires the descriptor heap feature");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkBindHeapInfoEXT pBindInfo = VkBindHeapInfoEXT.calloc(stack)
                    .sType$Default()
                    .heapRange(r -> r.address$(bindInfo.heapRange().address()).size(bindInfo.heapRange().size().bytes()))
                    .reservedRangeOffset(bindInfo.reservedRangeOffset().bytes())
                    .reservedRangeSize(bindInfo.reservedRangeSize().bytes());
            EXTDescriptorHeap.vkCmdBindSamplerHeapEXT(this.handle, pBindInfo);
        }
    }

    /// Pushes data (not to be confused with [#pushConstants])
    /// Requires the descriptor heap feature
    @Contract(mutates = "this")
    public void pushData(@NotNull VulkanicPushDataInfo pushInfo) {
        if (!device.features().supportsDescriptorHeap()) throw new UnsupportedOperationException("VulkanicCommandBuffer#pushData requires the descriptor heap feature");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            EXTDescriptorHeap.vkCmdPushDataEXT(this.handle, pushInfo.build(stack));
        }
    }

    /// Traces rays with the currently bound ray tracing pipeline.
    @Contract(mutates = "this")
    public void traceRays(
            @NotNull VulkanicStridedDeviceAddressRegion raygenShaderBindingTable,
            @NotNull VulkanicStridedDeviceAddressRegion missShaderBindingTable,
            @NotNull VulkanicStridedDeviceAddressRegion hitShaderBindingTable,
            @NotNull VulkanicStridedDeviceAddressRegion callableShaderBindingTable,
            int width, int height, int depth
    ) {
        if (!device.features().supportsRayTracingPipeline()) throw new UnsupportedOperationException("VulkanicCommandBuffer#traceRays requires the ray tracing pipeline feature");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            KHRRayTracingPipeline.vkCmdTraceRaysKHR(
                    this.handle,
                    raygenShaderBindingTable.build(stack),
                    missShaderBindingTable.build(stack),
                    hitShaderBindingTable.build(stack),
                    callableShaderBindingTable.build(stack),
                    width, height, depth
            );
        }
    }

    /// Traces rays according to the indirect buffer. This api is unfinished, so see vulkan spec for more detailed explanation. This runs vkCmdTraceRaysIndirectKHR
    @Contract(mutates = "this")
    public void traceRaysIndirect(
            @NotNull VulkanicStridedDeviceAddressRegion raygenShaderBindingTable,
            @NotNull VulkanicStridedDeviceAddressRegion missShaderBindingTable,
            @NotNull VulkanicStridedDeviceAddressRegion hitShaderBindingTable,
            @NotNull VulkanicStridedDeviceAddressRegion callableShaderBindingTable,
            @NotNull VulkanicBuffer indirect
    ) {
        if (!device.features().supportsRayTracingPipeline()) throw new UnsupportedOperationException("VulkanicCommandBuffer#traceRays requires the ray tracing pipeline feature");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            KHRRayTracingPipeline.vkCmdTraceRaysIndirectKHR(
                    this.handle,
                    raygenShaderBindingTable.build(stack),
                    missShaderBindingTable.build(stack),
                    hitShaderBindingTable.build(stack),
                    callableShaderBindingTable.build(stack),
                    indirect.deviceAddress()
            );
        }
    }

    /// Sets the ray tracing pipeline stack size
    @Contract(mutates = "this")
    public void setRayTracingPipelineStackSize(int pipelineStackSize) {
        if (!device.features().supportsRayTracingPipeline()) throw new UnsupportedOperationException("VulkanicCommandBuffer#traceRays requires the ray tracing pipeline feature");
        KHRRayTracingPipeline.vkCmdSetRayTracingPipelineStackSizeKHR(this.handle, pipelineStackSize);
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public @NotNull VkCommandBuffer handle() {
        return handle;
    }

    @Contract(pure = true)
    public @NotNull VulkanicCommandBufferLevel level() {
        return level;
    }

    @Override
    public void close() {
        pool.freeCommandBuffer(this);
    }

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.COMMAND_BUFFER;
    }
}
