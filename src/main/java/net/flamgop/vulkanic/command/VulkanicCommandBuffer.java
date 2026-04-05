package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.exception.VulkanicResult;
import net.flamgop.vulkanic.memory.VulkanicIndexType;
import net.flamgop.vulkanic.memory.copy.VulkanicBufferImageCopy;
import net.flamgop.vulkanic.memory.image.VulkanicImageSubresourceRange;
import net.flamgop.vulkanic.pipeline.*;
import net.flamgop.vulkanic.memory.VulkanicBuffer;
import net.flamgop.vulkanic.memory.image.VulkanicFilter;
import net.flamgop.vulkanic.memory.image.VulkanicImage;
import net.flamgop.vulkanic.memory.image.VulkanicImageLayout;
import net.flamgop.vulkanic.pipeline.descriptor.VulkanicDescriptorSet;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicRect2D;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicViewport;
import net.flamgop.vulkanic.util.EnumIntBitset;
import net.flamgop.vulkanic.util.EnumLongBitset;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.List;


import static org.lwjgl.vulkan.VK11.*;
import static org.lwjgl.vulkan.VK13.*;

@SuppressWarnings("UnusedReturnValue")
public class VulkanicCommandBuffer implements AutoCloseable {

    private final VulkanicDevice device;
    private final VulkanicCommandPool pool;
    private final VkCommandBuffer handle;

    @ApiStatus.Internal
    public VulkanicCommandBuffer(@NotNull VulkanicCommandPool pool, @NotNull VkCommandBuffer handle) {
        this.device = pool.device();
        this.pool = pool;
        this.handle = handle;
    }

    public @NotNull VulkanicResult begin(@NotNull VkCommandBufferBeginInfo beginInfo) {
        return pool.beginCommandBuffer(this, beginInfo);
    }

    public @NotNull VulkanicResult begin(@NotNull EnumIntBitset<VulkanicCommandBufferUsageFlag> flags) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            return pool.beginCommandBuffer(this, VkCommandBufferBeginInfo.calloc(stack).flags(flags.mask()).sType$Default());
        }
    }

    public @NotNull VulkanicResult reset(int flags) {
        return pool.resetCommandBuffer(this, flags);
    }

    public @NotNull VulkanicResult end() {
        return pool.endCommandBuffer(this);
    }

    public void bindPipeline(@NotNull VulkanicPipelineBindPoint pipelineBindPoint, @NotNull VulkanicPipeline pipeline) {
        vkCmdBindPipeline(handle, pipelineBindPoint.qualifier(), pipeline.handle());
    }

    public void bindDescriptorSet(@NotNull VulkanicPipelineBindPoint pipelineBindPoint, @NotNull VulkanicPipelineLayout layout, int firstSet, @NotNull VulkanicDescriptorSet set, int @Nullable [] dynamicOffsets) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            vkCmdBindDescriptorSets(handle, pipelineBindPoint.qualifier(), layout.handle(), firstSet, stack.longs(set.handle()), dynamicOffsets != null ? stack.ints(dynamicOffsets) : null);
        }
    }

    public void bindDescriptorSets(@NotNull VulkanicPipelineBindPoint pipelineBindPoint, @NotNull VulkanicPipelineLayout layout, int firstSet, @NotNull List<VulkanicDescriptorSet> sets, int @Nullable [] dynamicOffsets) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pSets = stack.callocLong(sets.size());
            for (VulkanicDescriptorSet set : sets) {
                pSets.put(set.handle());
            }
            vkCmdBindDescriptorSets(handle, pipelineBindPoint.qualifier(), layout.handle(), firstSet, pSets, dynamicOffsets != null ? stack.ints(dynamicOffsets) : null);
        }
    }

    public void pushConstants(@NotNull VulkanicPipelineLayout layout, EnumIntBitset<VulkanicShaderStage> stageFlags, int offset, @NotNull ByteBuffer values) {
        vkCmdPushConstants(handle, layout.handle(), stageFlags.mask(), offset, values);
    }

    public void dispatch(int groupCountX, int groupCountY, int groupCountZ) {
        vkCmdDispatch(handle, groupCountX, groupCountY, groupCountZ);
    }

    public void dispatchIndirect(@NotNull VulkanicBuffer buffer, long offset) {
        vkCmdDispatchIndirect(handle, buffer.handle(), offset);
    }

    public void setViewport(int firstViewport, @NotNull VulkanicViewport @NotNull ... viewports) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkViewport.Buffer pViewports = VkViewport.calloc(viewports.length, stack);
            for (int i = 0; i < viewports.length; i++) {
                viewports[i].get(pViewports.get(i));
            }
            vkCmdSetViewport(handle, firstViewport, pViewports);
        }
    }

    public void setScissor(int firstScissor, @NotNull VulkanicRect2D @NotNull ... scissors) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkRect2D.Buffer pScissors = VkRect2D.calloc(scissors.length, stack);
            for (int i = 0; i < scissors.length; i++) {
                scissors[i].get(pScissors.get(i));
            }
            vkCmdSetScissor(handle, firstScissor, pScissors);
        }
    }

    public void setLineWidth(float lineWidth) {
        vkCmdSetLineWidth(handle, lineWidth);
    }

    public void setDepthBias(float depthBiasConstantFactor, float depthBiasClamp, float depthBiasSlopeFactor) {
        vkCmdSetDepthBias(handle, depthBiasConstantFactor, depthBiasClamp, depthBiasSlopeFactor);
    }

    public void setBlendConstants(@NotNull FloatBuffer blendConstants) {
        vkCmdSetBlendConstants(handle, blendConstants);
    }

    public void setDepthBounds(float minDepthBounds, float maxDepthBounds) {
        vkCmdSetDepthBounds(handle, minDepthBounds, maxDepthBounds);
    }

    public void setStencilCompareMask(int faceMask, int compareMask) {
        vkCmdSetStencilCompareMask(handle, faceMask, compareMask);
    }

    public void setStencilWriteMask(int faceMask, int writeMask) {
        vkCmdSetStencilWriteMask(handle, faceMask, writeMask);
    }

    public void setStencilReference(int faceMask, int reference) {
        vkCmdSetStencilReference(handle, faceMask, reference);
    }

    public void bindVertexBuffers(int firstBinding, @NotNull VulkanicBuffer @NotNull [] buffers, long @NotNull [] offsets) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pBuffers = stack.longs(Arrays.stream(buffers).mapToLong(VulkanicBuffer::handle).toArray());
            LongBuffer pOffsets = stack.longs(offsets);
            vkCmdBindVertexBuffers(handle, firstBinding, pBuffers, pOffsets);
        }
    }

    public void bindIndexBuffer(@NotNull VulkanicBuffer buffer, long offset, @NotNull VulkanicIndexType indexType) {
        vkCmdBindIndexBuffer(handle, buffer.handle(), offset, indexType.qualifier());
    }

    public void draw(int vertexCount, int instanceCount, int firstVertex, int firstInstance) {
        vkCmdDraw(handle, vertexCount, instanceCount, firstVertex, firstInstance);
    }

    public void drawIndexed(int indexCount, int instanceCount, int firstIndex, int vertexOffset, int firstInstance) {
        vkCmdDrawIndexed(handle, indexCount, instanceCount, firstIndex, vertexOffset, firstInstance);
    }

    public void drawIndirect(@NotNull VulkanicBuffer buffer, long offset, int drawCount, int stride) {
        vkCmdDrawIndirect(handle, buffer.handle(), offset, drawCount, stride);
    }

    public void drawIndexedIndirect(@NotNull VulkanicBuffer buffer, long offset, int drawCount, int stride) {
        vkCmdDrawIndexedIndirect(handle, buffer.handle(), offset, drawCount, stride);
    }

    public void blitImage(@NotNull VulkanicImage srcImage, @NotNull VulkanicImageLayout srcLayout, @NotNull VulkanicImage dstImage, @NotNull VulkanicImageLayout dstLayout, @NotNull VkImageBlit.Buffer pRegions, @NotNull VulkanicFilter filter) {
        vkCmdBlitImage(handle, srcImage.handle(), srcLayout.qualifier(), dstImage.handle(), dstLayout.qualifier(), pRegions, filter.qualifier());
    }

    public void clearDepthStencilImage(@NotNull VulkanicImage image, @NotNull VulkanicImageLayout layout, @NotNull VkClearDepthStencilValue pDepthStencil, @NotNull VkImageSubresourceRange.Buffer pRanges) {
        vkCmdClearDepthStencilImage(handle, image.handle(), layout.qualifier(), pDepthStencil, pRanges);
    }

    public void clearAttachments(@NotNull VkClearAttachment.Buffer pAttachments, @NotNull VkClearRect.Buffer pRects) {
        vkCmdClearAttachments(handle, pAttachments, pRects);
    }

    public void resolveImage(@NotNull VulkanicImage srcImage, @NotNull VulkanicImageLayout srcLayout, @NotNull VulkanicImage dstImage, @NotNull VulkanicImageLayout dstLayout, @NotNull VkImageResolve.Buffer pRegions) {
        vkCmdResolveImage(handle, srcImage.handle(), srcLayout.qualifier(), dstImage.handle(), dstLayout.qualifier(), pRegions);
    }

    public void beginRenderPass(@NotNull VkRenderPassBeginInfo pRenderPassBegin, int contents) {
        vkCmdBeginRenderPass(handle, pRenderPassBegin, contents);
    }

    public void nextSubpass(int contents) {
        vkCmdNextSubpass(handle, contents);
    }

    public void endRenderPass() {
        vkCmdEndRenderPass(handle);
    }

    public void updateBuffer(@NotNull VulkanicBuffer buffer, long dstOffset, @NotNull ByteBuffer data) {
        vkCmdUpdateBuffer(handle, buffer.handle(), dstOffset, data);
    }

    public void copyBuffer(@NotNull VulkanicBuffer srcBuffer, @NotNull VulkanicBuffer dstBuffer, VkBufferCopy.Buffer pRegions) {
        vkCmdCopyBuffer(handle, srcBuffer.handle(), dstBuffer.handle(), pRegions);
    }

    public void copyImage(@NotNull VulkanicImage srcImage, @NotNull VulkanicImageLayout srcLayout, @NotNull VulkanicImage dstImage, @NotNull VulkanicImageLayout dstLayout, @NotNull VkImageCopy.Buffer pRegions) {
        vkCmdCopyImage(handle, srcImage.handle(), srcLayout.qualifier(), dstImage.handle(), dstLayout.qualifier(), pRegions);
    }

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
                        .imageOffset(o -> o.set(region.imageOffset().x, region.imageOffset().y, region.imageOffset().z))
                        .imageExtent(e -> e.set(region.imageExtent().x, region.imageExtent().y, region.imageExtent().z));
            }
            vkCmdCopyBufferToImage(handle, srcBuffer.handle(), dstImage.handle(), dstLayout.qualifier(), pRegions);
        }
    }

    public void copyImageToBuffer(@NotNull VulkanicImage srcImage, @NotNull VulkanicImageLayout srcLayout, @NotNull VulkanicBuffer dstBuffer, VkBufferImageCopy.Buffer pRegions) {
        vkCmdCopyImageToBuffer(handle, srcImage.handle(), srcLayout.qualifier(), dstBuffer.handle(), pRegions);
    }

    public void fillBuffer(@NotNull VulkanicBuffer dstBuffer, long dstOffset, long size, int data) {
        vkCmdFillBuffer(handle, dstBuffer.handle(), dstOffset, size, data);
    }

    public void pipelineBarrier(int srcStageMask, int dstStageMask, int dependencyFlags, @Nullable VkMemoryBarrier.Buffer pMemoryBarriers, @Nullable VkBufferMemoryBarrier.Buffer pBufferMemoryBarriers, @Nullable VkImageMemoryBarrier.Buffer pImageMemoryBarriers) {
        vkCmdPipelineBarrier(handle, srcStageMask, dstStageMask, dependencyFlags, pMemoryBarriers, pBufferMemoryBarriers, pImageMemoryBarriers);
    }

    @SuppressWarnings("resource")
    public void pipelineBarrier(@NotNull VulkanicDependencyInfo info) {
        if (!device.features().supportsSynchronization2()) {
            throw new UnsupportedOperationException("CommandBuffer#pipelineBarrier requires the synchronization2 device extension/feature to be enabled!");
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

    public void executeCommands(PointerBuffer pCommandBuffers) {
        vkCmdExecuteCommands(handle, pCommandBuffers);
    }

    @SuppressWarnings("resource")
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

    public void endRendering() {
        if (!device.features().supportsDynamicRendering()) {
            throw new UnsupportedOperationException("VulkanicCommandBuffer#endRendering requires the dynamicRendering device feature.");
        }
        vkCmdEndRendering(handle);
    }

    public void drawMeshTasksEXT(int groupCountX, int groupCountY, int groupCountZ) {
        if (!device.features().supportsMeshShader()) {
            throw new UnsupportedOperationException("VulkanicCommandBuffer#drawMeshTasksEXT requires the meshShader device feature.");
        }
        EXTMeshShader.vkCmdDrawMeshTasksEXT(this.handle, groupCountX, groupCountY, groupCountZ);
    }

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

    public void transitionImageLayout(
            @NotNull VulkanicImage image,
            @NotNull VulkanicImageLayout oldLayout,
            @NotNull VulkanicImageLayout newLayout,
            @NotNull EnumLongBitset<VulkanicPipelineStageFlag> srcStage,
            @NotNull EnumLongBitset<VulkanicAccessFlag> srcAccessMask,
            @NotNull EnumLongBitset<VulkanicPipelineStageFlag> dstStage,
            @NotNull EnumLongBitset<VulkanicAccessFlag> dstAccessMask
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
                                image, new VulkanicImageSubresourceRange(image.aspectMask(), 0, VK_REMAINING_MIP_LEVELS, 0, VK_REMAINING_ARRAY_LAYERS)
                        )
                )
        ));
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public @NotNull VkCommandBuffer handle() {
        return handle;
    }

    @Override
    public void close() {
        pool.freeCommandBuffer(this);
    }
}
