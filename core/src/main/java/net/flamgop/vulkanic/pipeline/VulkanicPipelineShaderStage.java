package net.flamgop.vulkanic.pipeline;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.vulkan.VkSpecializationInfo;

public record VulkanicPipelineShaderStage(
        @NotNull VulkanicShaderStage stage,
        @NotNull String entrypoint,
        @NotNull VulkanicShaderModule module,
        @Nullable VkSpecializationInfo specializationInfo,
        int flags,
        long pNext
) {
    public VulkanicPipelineShaderStage(@NotNull VulkanicShaderStage stage,
                                       @NotNull String entrypoint,
                                       @NotNull VulkanicShaderModule module) {
        this(stage, entrypoint, module, null, 0, 0);
    }
}
