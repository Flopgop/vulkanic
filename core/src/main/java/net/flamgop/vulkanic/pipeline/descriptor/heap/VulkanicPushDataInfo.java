package net.flamgop.vulkanic.pipeline.descriptor.heap;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPushDataInfoEXT;

import java.nio.ByteBuffer;

public record VulkanicPushDataInfo(int offset, ByteBuffer data) {
    public VkPushDataInfoEXT build(MemoryStack stack) {
        return VkPushDataInfoEXT.calloc(stack)
                .sType$Default()
                .offset(offset)
                .data(range -> range.address$(data));
    }
}
