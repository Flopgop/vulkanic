package net.flamgop.vulkanic.pipeline.graphics;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineDynamicStateCreateInfo;

import java.util.List;

public record VulkanicPipelineDynamicState(List<VulkanicDynamicState> states) {

    public static final List<VulkanicDynamicState> DYNAMIC_RASTERIZATION_STATE = List.of(
            VulkanicDynamicState.DEPTH_CLAMP_ENABLE_EXT,
            VulkanicDynamicState.POLYGON_MODE_EXT,
            VulkanicDynamicState.RASTERIZER_DISCARD_ENABLE,
            VulkanicDynamicState.CULL_MODE,
            VulkanicDynamicState.FRONT_FACE,
            VulkanicDynamicState.DEPTH_BIAS_ENABLE,
            VulkanicDynamicState.DEPTH_BIAS,
            VulkanicDynamicState.LINE_WIDTH
    );

    public static final List<VulkanicDynamicState> DYNAMIC_MULTISAMPLE_STATE = List.of(
            VulkanicDynamicState.RASTERIZATION_SAMPLES_EXT,
            VulkanicDynamicState.SAMPLE_MASK_EXT,
            VulkanicDynamicState.ALPHA_TO_COVERAGE_ENABLE_EXT,
            VulkanicDynamicState.ALPHA_TO_ONE_ENABLE_EXT
    );

    public @NotNull VkPipelineDynamicStateCreateInfo build(MemoryStack stack) {
        return VkPipelineDynamicStateCreateInfo.calloc(stack)
                .sType$Default()
                .pDynamicStates(stack.ints(states.stream().mapToInt(VulkanicDynamicState::qualifier).toArray()));
    }
}
