package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.core.feature.VulkanicDeviceFeatures;
import net.flamgop.vulkanic.memory.VulkanicFormat;
import net.flamgop.vulkanic.pipeline.descriptor.heap.VulkanicDescriptorSetAndBindingMapping;
import net.flamgop.vulkanic.pipeline.graphics.*;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.*;

import java.util.List;
import java.util.OptionalInt;

public record VulkanicGraphicsPipelineCreateInfo(
        @NotNull EnumIntBitset<VulkanicPipelineCreateFlag> flags,
        @Nullable VulkanicPipelineLayout layout,
        @Nullable VulkanicRenderPass renderPass,
        int subpass,
        @NotNull List<VulkanicPipelineShaderStage> stages,
        @Nullable VulkanicVertexInputState vertexInputState,
        @Nullable VulkanicInputAssemblyState inputAssemblyState,
        @Nullable VulkanicViewportState viewportState,
        @Nullable VulkanicRasterizationState rasterizationState,
        @Nullable VulkanicMultisampleState multisampleState,
        @Nullable VulkanicDepthStencilState depthStencilState,
        @Nullable VulkanicColorBlendState colorBlendState,
        @Nullable VulkanicPipelineDynamicState dynamicState,
        @Nullable VulkanicPipeline basePipeline,
        @NotNull OptionalInt basePipelineIndex,
        @Nullable VulkanicDescriptorSetAndBindingMapping descriptorSetAndBindingMapping,
        @Nullable VulkanicPipelineRenderingInfo renderingInfo,
        long pNext
) {
    public void validate(VulkanicDeviceFeatures features) {
        if (layout == null && (!features.supportsDescriptorHeap() || descriptorSetAndBindingMapping == null)) {
            throw new UnsupportedOperationException("if layout is null, the descriptor heap feature must be enabled and a descriptor set and binding mapping must be provided.");
        }

        if (renderPass == null && renderingInfo == null) {
            throw new UnsupportedOperationException("if renderPass is null, the dynamic rendering info must be provided");
        }
    }

    @SuppressWarnings("resource")
    @Contract(mutates = "param1,param2", value = "_, _ -> param1")
    public VkGraphicsPipelineCreateInfo build(VkGraphicsPipelineCreateInfo pCreateInfo, MemoryStack stack) {
        VkPipelineShaderStageCreateInfo.Buffer shaderStages = VkPipelineShaderStageCreateInfo.calloc(stages.size(), stack);
        for (int i = 0; i < stages.size(); i++) {
            VulkanicPipelineShaderStage stageInfo = stages.get(i);
            shaderStages.get(i)
                    .sType$Default()
                    .stage(stageInfo.stage().flag())
                    .module(stageInfo.module().handle())
                    .pName(stack.UTF8(stageInfo.entrypoint()))
                    .pSpecializationInfo(stageInfo.specializationInfo());
        }

        long pNext = this.pNext;
        if (renderingInfo != null) {
            VkPipelineRenderingCreateInfoKHR pRenderingInfo = VkPipelineRenderingCreateInfoKHR.calloc(stack)
                    .sType$Default()
                    .viewMask(renderingInfo.viewMask())
                    .colorAttachmentCount(renderingInfo.colorAttachmentFormats().size())
                    .pColorAttachmentFormats(stack.ints(renderingInfo.colorAttachmentFormats().stream().mapToInt(VulkanicFormat::qualifier).toArray()))
                    .depthAttachmentFormat(renderingInfo.depthAttachmentFormat() != null ? renderingInfo.depthAttachmentFormat().qualifier() : 0)
                    .stencilAttachmentFormat(renderingInfo.stencilAttachmentFormat() != null ? renderingInfo.stencilAttachmentFormat().qualifier() : 0)
                    .pNext(pNext);
            pNext = pRenderingInfo.address();
        }

        if (descriptorSetAndBindingMapping != null) {
            pNext = descriptorSetAndBindingMapping.build(VkDescriptorSetAndBindingMappingEXT.calloc(stack), stack)
                    .pNext(pNext)
                    .address();
        }

        return pCreateInfo
                .sType$Default()
                .flags(flags.mask())
                .pStages(shaderStages)
                .pVertexInputState(vertexInputState != null ? vertexInputState.build(stack) : null)
                .pInputAssemblyState(inputAssemblyState != null ? inputAssemblyState.build(stack) : null)
                .pViewportState(viewportState != null ? viewportState.build(stack) : null)
                .pRasterizationState(rasterizationState != null ? rasterizationState.build(stack) : null)
                .pMultisampleState(multisampleState != null ? multisampleState.build(stack) : null)
                .pDepthStencilState(depthStencilState != null ? depthStencilState.build(stack) : null)
                .pColorBlendState(colorBlendState != null ? colorBlendState.build(stack) : null)
                .pDynamicState(dynamicState != null ? dynamicState.build(stack) : null)
                .layout(layout != null ? layout.handle() : VK10.VK_NULL_HANDLE)
                .renderPass(renderPass != null ? renderPass.handle() : VK10.VK_NULL_HANDLE)
                .subpass(subpass)
                .basePipelineHandle(basePipeline != null ? basePipeline.handle() : VK10.VK_NULL_HANDLE)
                .basePipelineIndex(basePipelineIndex.orElse(-1))
                .pNext(pNext);
    }
}
