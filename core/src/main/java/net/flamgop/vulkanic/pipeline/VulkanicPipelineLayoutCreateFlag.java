package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.EXTGraphicsPipelineLibrary;
import org.lwjgl.vulkan.EXTShaderObject;

public enum VulkanicPipelineLayoutCreateFlag implements Bitmaskable<Integer> {
    /// Specifies that implementations must ensure the properties and/or absence of particular descriptor sets do not influence any other properties of the pipeline layout.
    INDEPENDENT_SETS_EXT(EXTGraphicsPipelineLibrary.VK_PIPELINE_LAYOUT_CREATE_INDEPENDENT_SETS_BIT_EXT),
    /// Specifies that this pipeline layout will only be used to draw with shader objects created with NO_TASK_SHADER_EXT
    NO_TASK_SHADER_KHR(EXTShaderObject.VK_SHADER_CREATE_NO_TASK_SHADER_BIT_EXT)
    ;

    private final int flag;
    VulkanicPipelineLayoutCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
