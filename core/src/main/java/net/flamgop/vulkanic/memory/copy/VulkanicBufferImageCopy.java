package net.flamgop.vulkanic.memory.copy;

import net.flamgop.vulkanic.math.Int3;
import net.flamgop.vulkanic.memory.image.VulkanicImageSubresourceLayers;

public record VulkanicBufferImageCopy(long bufferOffset, int bufferRowLength, int bufferImageHeight, VulkanicImageSubresourceLayers imageSubresource, Int3 imageOffset, Int3 imageExtent) {
}
