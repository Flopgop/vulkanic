package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.*;

public enum VulkanicQueueFlag implements Bitmaskable<Integer> {
    GRAPHICS(VK10.VK_QUEUE_GRAPHICS_BIT),
    COMPUTE(VK10.VK_QUEUE_COMPUTE_BIT),
    TRANSFER(VK10.VK_QUEUE_TRANSFER_BIT),
    SPARSE_BINDING(VK10.VK_QUEUE_SPARSE_BINDING_BIT),

    PROTECTED(VK11.VK_QUEUE_PROTECTED_BIT),

    VIDEO_DECODE_KHR(KHRVideoDecodeQueue.VK_QUEUE_VIDEO_DECODE_BIT_KHR),

    VIDEO_ENCODE_KHR(KHRVideoEncodeQueue.VK_QUEUE_VIDEO_ENCODE_BIT_KHR),

    OPTICAL_FLOW_NV(NVOpticalFlow.VK_QUEUE_OPTICAL_FLOW_BIT_NV),

    DATA_GRAPH_ARM(ARMDataGraph.VK_QUEUE_DATA_GRAPH_BIT_ARM)

    ;

    private final int flag;
    VulkanicQueueFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
