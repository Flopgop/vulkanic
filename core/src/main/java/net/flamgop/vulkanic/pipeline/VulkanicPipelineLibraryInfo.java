package net.flamgop.vulkanic.pipeline;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineLibraryCreateInfoKHR;

import java.nio.LongBuffer;
import java.util.List;

public record VulkanicPipelineLibraryInfo(
        List<VulkanicPipeline> libraries
) {
    public VkPipelineLibraryCreateInfoKHR build(MemoryStack stack) {
        LongBuffer pLibraries = stack.callocLong(libraries.size());
        for (int i = 0; i < libraries.size(); i++) {
            pLibraries.put(i, libraries.get(i).handle());
        }
        return VkPipelineLibraryCreateInfoKHR.calloc(stack)
                .sType$Default()
                .pLibraries(pLibraries);
    }
}
