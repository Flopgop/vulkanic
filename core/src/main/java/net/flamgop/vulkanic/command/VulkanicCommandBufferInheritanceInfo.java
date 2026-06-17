package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.pipeline.graphics.renderpass.VulkanicFramebuffer;
import net.flamgop.vulkanic.pipeline.graphics.renderpass.VulkanicRenderPass;
import net.flamgop.vulkanic.sync.VulkanicQueryControlFlag;
import net.flamgop.vulkanic.sync.VulkanicQueryPipelineStatisticFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/// If this command buffer is a secondary command buffer, then the {@link VulkanicCommandBufferInheritanceInfo} object defines any state that will be inherited from the primary command buffer.
/// @param renderPass is a {@link VulkanicRenderPass} object defining which render passes the {@link VulkanicCommandBuffer} will be compatible with and *can** be executed within
/// @param subpass is the index of the subpass within the render pass instance that the {@link VulkanicCommandBuffer} will be executed within.
/// @param framebuffer **can** refer to the {@link VulkanicFramebuffer} object that the {@link VulkanicCommandBuffer} will be rendering to if it is executed within a render pass instance. It **can** be `null` if the framebuffer is not known.
/// @param occlusionQueryEnable specifies whether the command buffer **can** be executed while an occlusion query is active in the primary command buffer. If this is `true`, then this command buffer **can** be executed whether the primary command buffer has an occlusion query active or not. If this is `false`, then the primary command buffer **must** not have an occlusion query active.
/// @param queryFlags specifies the query flags that **can** be used by an active occlusion query in the primary command buffer when this secondary command buffer is executed. If this value includes the {@link VulkanicQueryControlFlag#PRECISE} flag, then the active query **can** return boolean results or actual sample counts. If this flag is not set, then the active query **must** not use the {@link VulkanicQueryControlFlag#PRECISE} flag.
/// @param pipelineStatistics is a bitset of {@link VulkanicQueryPipelineStatisticFlag} specifying the et of pipeline statistics that **can** be counted by an active query in the primary command buffer when this secondary command buffer is executed. If this set includes a given flag, then this command buffer **can** be executed whether the primary command buffer as a pipeline statistics query active that includes this bit or not. If this value excludes a given bit, then the active pipeline statistics query **must** not be from a query pool that counts that statistic.
/// @see VulkanicCommandBuffer#begin(VulkanicCommandBufferBeginInfo)
public record VulkanicCommandBufferInheritanceInfo(
        @NotNull VulkanicRenderPass renderPass,
        int subpass,
        @Nullable VulkanicFramebuffer framebuffer,
        boolean occlusionQueryEnable,
        @NotNull EnumIntBitset<VulkanicQueryControlFlag> queryFlags,
        @NotNull EnumIntBitset<VulkanicQueryPipelineStatisticFlag> pipelineStatistics
) {
}
