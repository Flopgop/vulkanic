package net.flamgop.vulkanic.pipeline.descriptor.heap;

import java.nio.ByteBuffer;

public record VulkanicPushDataInfo(int offset, ByteBuffer data) {
}
