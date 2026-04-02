package net.flamgop.vulkanic.pipeline.graphics;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineColorBlendAttachmentState;
import org.lwjgl.vulkan.VkPipelineColorBlendStateCreateInfo;

import java.util.List;

public record VulkanicColorBlendState(
        boolean logicOpEnable, VulkanicLogicOp logicOp,
        int attachmentCount, List<VulkanicColorBlendAttachmentState> attachments
) {
    public @NotNull VkPipelineColorBlendStateCreateInfo build(MemoryStack stack) {
        VkPipelineColorBlendAttachmentState.Buffer pAttachments = VkPipelineColorBlendAttachmentState.calloc(attachments.size(), stack);
        for (int i = 0; i < attachments.size(); i++) {
            attachments.get(i).get(pAttachments.get(i));
        }

        return VkPipelineColorBlendStateCreateInfo.calloc(stack)
                .sType$Default()
                .logicOpEnable(logicOpEnable)
                .logicOp(logicOp.qualifier())
                .attachmentCount(attachmentCount)
                .pAttachments(pAttachments);
    }
}
