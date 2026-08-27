package net.flamgop.vulkanic.core.queue;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.*;

public enum VulkanicQueueFlag implements Bitmaskable<Integer> {
    /// A queue that is capable of submitting graphics operations
    GRAPHICS(VK10.VK_QUEUE_GRAPHICS_BIT),
    /// A queue that is capable of submitting compute operations
    COMPUTE(VK10.VK_QUEUE_COMPUTE_BIT),
    /// A queue that is capable of submitting transfer operations
    TRANSFER(VK10.VK_QUEUE_TRANSFER_BIT),
    /// A queue that is capable of sparse binding
    SPARSE_BINDING(VK10.VK_QUEUE_SPARSE_BINDING_BIT),

    /// A protected queue
    PROTECTED(VK11.VK_QUEUE_PROTECTED_BIT),

    /// A queue that is capable of submitting video decode operations
    VIDEO_DECODE_KHR(KHRVideoDecodeQueue.VK_QUEUE_VIDEO_DECODE_BIT_KHR),

    /// A queue that is capable of submitting video encode operations
    VIDEO_ENCODE_KHR(KHRVideoEncodeQueue.VK_QUEUE_VIDEO_ENCODE_BIT_KHR),

    /// A queue that is capable of submitting optical flow operations
    OPTICAL_FLOW_NV(NVOpticalFlow.VK_QUEUE_OPTICAL_FLOW_BIT_NV),

    /// A queue that is capable of submitting data graph operations
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
