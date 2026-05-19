package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.core.feature.VulkanicDeviceFeatures;
import net.flamgop.vulkanic.pipeline.descriptor.heap.VulkanicDescriptorSetAndBindingMapping;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkComputePipelineCreateInfo;
import org.lwjgl.vulkan.VkDescriptorSetAndBindingMappingEXT;

import java.util.OptionalInt;

public record VulkanicComputePipelineCreateInfo(
        @NotNull EnumIntBitset<VulkanicPipelineCreateFlag> flags,
        @Nullable VulkanicPipelineLayout layout,
        @NotNull VulkanicPipelineShaderStage stageInfo,
        @Nullable VulkanicPipeline basePipeline,
        @NotNull OptionalInt basePipelineIndex,
        @Nullable VulkanicDescriptorSetAndBindingMapping descriptorSetAndBindingMapping,
        long pNext
) {
    public VulkanicComputePipelineCreateInfo(
            @NotNull EnumIntBitset<VulkanicPipelineCreateFlag> flags,
            @Nullable VulkanicPipelineLayout layout,
            @NotNull VulkanicPipelineShaderStage stageInfo
    ) {
        this(flags, layout, stageInfo, null, OptionalInt.empty(), null, 0);
    }

    public void validate(VulkanicDeviceFeatures features) {
        if (layout == null && (!features.supportsDescriptorHeap() || descriptorSetAndBindingMapping == null)) {
            throw new UnsupportedOperationException("if layout is null, the descriptor heap feature must be enabled and a descriptor set and binding mapping must be provided.");
        }
    }

    @SuppressWarnings("resource")
    @Contract(mutates = "param1,param2", value = "_, _ -> param1")
    public VkComputePipelineCreateInfo build(VkComputePipelineCreateInfo pCreateInfo, MemoryStack stack) {
        long pNext = this.pNext;
        if (descriptorSetAndBindingMapping != null) {
            pNext = descriptorSetAndBindingMapping.build(VkDescriptorSetAndBindingMappingEXT.calloc(stack), stack)
                    .pNext(pNext)
                    .address();
        }

        return pCreateInfo
                .sType$Default()
                .flags(flags.mask())
                .layout(layout != null ? layout.handle() : VK10.VK_NULL_HANDLE)
                .stage(stage ->
                        stage.sType$Default()
                                .pName(stack.UTF8(stageInfo.entrypoint()))
                                .pSpecializationInfo(stageInfo.specializationInfo())
                                .stage(stageInfo.stage().flag())
                                .module(stageInfo.module().handle())
                                .flags(stageInfo.flags())
                                .pNext(stageInfo.pNext())
                )
                .basePipelineHandle(basePipeline != null ? basePipeline.handle() : VK10.VK_NULL_HANDLE)
                .basePipelineIndex(basePipelineIndex.orElse(-1))
                .pNext(pNext);
    }
}
