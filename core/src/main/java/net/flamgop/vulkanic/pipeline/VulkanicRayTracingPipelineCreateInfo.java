package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.core.feature.VulkanicDeviceFeatures;
import net.flamgop.vulkanic.pipeline.descriptor.heap.VulkanicDescriptorSetAndBindingMapping;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicPipelineDynamicState;
import net.flamgop.vulkanic.pipeline.raytracing.VulkanicRayTracingPipelineInterfaceInfo;
import net.flamgop.vulkanic.pipeline.raytracing.VulkanicRayTracingShaderGroup;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.*;

import java.util.List;
import java.util.OptionalInt;

public record VulkanicRayTracingPipelineCreateInfo(
        @NotNull EnumIntBitset<VulkanicPipelineCreateFlag> flags,
        @Nullable VulkanicPipelineLayout layout,

        @NotNull List<VulkanicPipelineShaderStage> stages,
        @NotNull List<VulkanicRayTracingShaderGroup> groups,
        int maxPipelineRayRecursionDepth,
        @Nullable VulkanicPipelineLibraryInfo libraryInfo,
        @NotNull VulkanicRayTracingPipelineInterfaceInfo libraryInterface,
        @Nullable VulkanicPipelineDynamicState dynamicState,
        @Nullable VulkanicPipeline basePipeline,
        @NotNull OptionalInt basePipelineIndex,
        @Nullable VulkanicDescriptorSetAndBindingMapping descriptorSetAndBindingMapping,
        long pNext
) {
    public void validate(VulkanicDeviceFeatures features) {
        if (!features.supportsRayTracingPipeline()) throw new UnsupportedOperationException("Ray tracing pipelines require the rayTracingPipeline feature");
        if (layout == null && (!features.supportsDescriptorHeap() || descriptorSetAndBindingMapping == null)) {
            throw new UnsupportedOperationException("if layout is null, the descriptor heap feature must be enabled and a descriptor set and binding mapping must be provided.");
        }
    }

    @SuppressWarnings("resource")
    @Contract(mutates = "param1,param2", value = "_, _ -> param1")
    public VkRayTracingPipelineCreateInfoKHR build(VkRayTracingPipelineCreateInfoKHR createInfo, MemoryStack stack) {
        VkPipelineShaderStageCreateInfo.Buffer pStages = VkPipelineShaderStageCreateInfo.calloc(stages.size(), stack);
        for (int i = 0; i < stages.size(); i++) {
            VulkanicPipelineShaderStage stageInfo = stages.get(i);
            pStages.get(i)
                    .sType$Default()
                    .stage(stageInfo.stage().flag())
                    .module(stageInfo.module().handle())
                    .pName(stack.UTF8(stageInfo.entrypoint()))
                    .pSpecializationInfo(stageInfo.specializationInfo());
        }

        VkRayTracingShaderGroupCreateInfoKHR.Buffer pGroups = VkRayTracingShaderGroupCreateInfoKHR.calloc(groups.size(), stack);
        for (int i = 0; i < groups.size(); i++) {
            VulkanicRayTracingShaderGroup groupInfo = groups.get(i);
            pGroups.get(i)
                    .sType$Default()
                    .type(groupInfo.type().qualifier())
                    .generalShader(groupInfo.generalShaderIndex())
                    .closestHitShader(groupInfo.closestHitShaderIndex())
                    .anyHitShader(groupInfo.anyHitShaderIndex())
                    .intersectionShader(groupInfo.intersectionShaderIndex());
        }

        long pNext = this.pNext;
        if (descriptorSetAndBindingMapping != null) {
            pNext = descriptorSetAndBindingMapping.build(VkDescriptorSetAndBindingMappingEXT.calloc(stack), stack)
                    .pNext(pNext)
                    .address();
        }

        return createInfo
                .sType$Default()
                .flags(flags.mask())
                .pStages(pStages)
                .pGroups(pGroups)
                .maxPipelineRayRecursionDepth(maxPipelineRayRecursionDepth)
                .pLibraryInfo(libraryInfo != null ? libraryInfo.build(stack) : null)
                .pLibraryInterface(libraryInterface.build(stack))
                .pDynamicState(dynamicState != null ? dynamicState.build(stack) : null)
                .basePipelineHandle(basePipeline != null ? basePipeline.handle() : VK10.VK_NULL_HANDLE)
                .basePipelineIndex(basePipelineIndex.orElse(-1))
                .pNext(pNext);
    }
}
