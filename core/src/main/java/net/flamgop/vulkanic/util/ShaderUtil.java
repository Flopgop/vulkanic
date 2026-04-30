package net.flamgop.vulkanic.util;

import net.flamgop.vulkanic.pipeline.VulkanicPipelineShaderStage;
import net.flamgop.vulkanic.pipeline.VulkanicShaderStage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShaderUtil {
    public static boolean containsPrimitiveStage(List<@NotNull VulkanicPipelineShaderStage> stages) {
        return stages.stream().anyMatch(s ->
                s.stage() == VulkanicShaderStage.VERTEX ||
                s.stage() == VulkanicShaderStage.MESH_EXT ||
                s.stage() == VulkanicShaderStage.MESH_NV
        );
    }

    public static boolean containsMultiplePrimitiveStages(List<@NotNull VulkanicPipelineShaderStage> stages) {
        return stages.stream().filter(s ->
                s.stage() == VulkanicShaderStage.VERTEX ||
                s.stage() == VulkanicShaderStage.MESH_EXT ||
                s.stage() == VulkanicShaderStage.MESH_NV
        ).count() > 1;
    }
}
