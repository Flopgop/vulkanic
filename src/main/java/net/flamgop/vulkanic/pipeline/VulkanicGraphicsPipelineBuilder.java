package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.pipeline.graphics.*;
import net.flamgop.vulkanic.util.ShaderUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class VulkanicGraphicsPipelineBuilder implements VulkanicPipelineBuilder {

    private final @NotNull VulkanicDevice device;
    private final @NotNull VulkanicPipelineLayout pipelineLayout;
    private final @Nullable VulkanicRenderPass renderPass;

    private int subpass = 0;
    private final @NotNull List<@NotNull VulkanicPipelineShaderStage> shaderStages = new ArrayList<>();
    private @Nullable VulkanicVertexInputState vertexInputState = null;
    private @Nullable VulkanicInputAssemblyState inputAssemblyState = null;
    private @Nullable VulkanicViewportState viewportState = null;
    private @Nullable VulkanicRasterizationState rasterizationState = null;
    private @Nullable VulkanicMultisampleState multisampleState = null;
    private @Nullable VulkanicDepthStencilState depthStencilState = null;
    private @Nullable VulkanicColorBlendState colorBlendState = null;
    private @Nullable VulkanicPipelineDynamicState dynamicState = null;
    private long pNext = 0;

    /// @see VulkanicDevice#createGraphicsPipelineBuilder
    @ApiStatus.Internal
    public VulkanicGraphicsPipelineBuilder(
            @NotNull VulkanicDevice device,
            @NotNull VulkanicPipelineLayout pipelineLayout,
            @Nullable VulkanicRenderPass renderPass
    ) {
        this.device = device;
        this.pipelineLayout = pipelineLayout;
        if (renderPass == null && !device.features().supportsDynamicRendering())
            throw new IllegalArgumentException("A graphics pipeline with a null render pass is only supported with the dynamic rendering feature!");
        this.renderPass = renderPass;
    }

    @Contract(mutates = "this", value = "_ -> this")
    public @NotNull VulkanicGraphicsPipelineBuilder subpass(int subpass) {
        this.subpass = subpass;
        return this;
    }

    @Contract(mutates = "this", value = "_ -> this")
    public @NotNull VulkanicGraphicsPipelineBuilder shaderStage(@NotNull VulkanicPipelineShaderStage shaderStage) {
        this.shaderStages.add(shaderStage);
        return this;
    }

    @Contract(mutates = "this", value = "_ -> this")
    public @NotNull VulkanicGraphicsPipelineBuilder vertexInputState(@Nullable VulkanicVertexInputState vertexInputState) {
        this.vertexInputState = vertexInputState;
        return this;
    }

    @Contract(mutates = "this", value = "_ -> this")
    public @NotNull VulkanicGraphicsPipelineBuilder inputAssemblyState(@Nullable VulkanicInputAssemblyState inputAssemblyState) {
        this.inputAssemblyState = inputAssemblyState;
        return this;
    }

    @Contract(mutates = "this", value = "_ -> this")
    public @NotNull VulkanicGraphicsPipelineBuilder viewportState(@Nullable VulkanicViewportState viewportState) {
        this.viewportState = viewportState;
        return this;
    }

    @Contract(mutates = "this", value = "_ -> this")
    public @NotNull VulkanicGraphicsPipelineBuilder rasterizationState(@Nullable VulkanicRasterizationState rasterizationState) {
        this.rasterizationState = rasterizationState;
        return this;
    }

    @Contract(mutates = "this", value = "_ -> this")
    public @NotNull VulkanicGraphicsPipelineBuilder multisampleState(@Nullable VulkanicMultisampleState multisampleState) {
        this.multisampleState = multisampleState;
        return this;
    }

    @Contract(mutates = "this", value = "_ -> this")
    public @NotNull VulkanicGraphicsPipelineBuilder depthStencilState(@Nullable VulkanicDepthStencilState depthStencilState) {
        this.depthStencilState = depthStencilState;
        return this;
    }

    @Contract(mutates = "this", value = "_ -> this")
    public @NotNull VulkanicGraphicsPipelineBuilder colorBlendState(@Nullable VulkanicColorBlendState colorBlendState) {
        this.colorBlendState = colorBlendState;
        return this;
    }

    @Contract(mutates = "this", value = "_ -> this")
    public @NotNull VulkanicGraphicsPipelineBuilder dynamicState(@Nullable VulkanicPipelineDynamicState dynamicState) {
        this.dynamicState = dynamicState;
        return this;
    }

    @Contract(mutates = "this", value = "_ -> this")
    public @NotNull VulkanicGraphicsPipelineBuilder pNext(long pNext) {
        this.pNext = pNext;
        return this;
    }

    @Override
    public @NotNull VulkanicGraphicsPipeline build() {
        if (viewportState != null && rasterizationState == null) throw new IllegalStateException("Viewport state is co-dependent on rasterization state!");
        if (shaderStages.isEmpty() || !ShaderUtil.containsPrimitiveStage(shaderStages)) throw new IllegalStateException("A graphics pipeline requires at least one primitive-producing shader stage. (i.e. VERTEX or MESH_EXT)");
        if (ShaderUtil.containsMultiplePrimitiveStages(shaderStages)) throw new IllegalStateException("A graphics pipeline may not contain more than one primitive-producing shader stage. (i.e. VERTEX or MESH_EXT)");

        if (rasterizationState == null) {
            boolean hasDynamicRasterState = dynamicState != null &&
                    new HashSet<>(dynamicState.states()).containsAll(VulkanicPipelineDynamicState.DYNAMIC_RASTERIZATION_STATE);
            if (!hasDynamicRasterState)
                throw new IllegalStateException(String.format("If rasterization state is null, dynamic state must contain all of: %s", VulkanicPipelineDynamicState.DYNAMIC_RASTERIZATION_STATE));
        }

        if (multisampleState == null) {
            boolean hasDynamicMultisampleState = dynamicState != null &&
                    new HashSet<>(dynamicState.states()).containsAll(VulkanicPipelineDynamicState.DYNAMIC_MULTISAMPLE_STATE);
            if (!hasDynamicMultisampleState)
                throw new IllegalStateException(String.format("If multisample state is null, dynamic state must contain all of: %s", VulkanicPipelineDynamicState.DYNAMIC_MULTISAMPLE_STATE));
        }

        return device.createGraphicsPipeline(
                pipelineLayout,
                renderPass, subpass,
                shaderStages,
                vertexInputState, inputAssemblyState,
                viewportState, rasterizationState,
                multisampleState, depthStencilState,
                colorBlendState, dynamicState,
                pNext
        );
    }
}
