package net.flamgop.vulkanic.memory.copy;

import net.flamgop.vulkanic.memory.image.VulkanicImageSubresourceLayers;
import org.joml.Vector3i;

public record VulkanicBufferImageCopy(long bufferOffset, int bufferRowLength, int bufferImageHeight, VulkanicImageSubresourceLayers imageSubresource, Vector3i imageOffset, Vector3i imageExtent) {
}
