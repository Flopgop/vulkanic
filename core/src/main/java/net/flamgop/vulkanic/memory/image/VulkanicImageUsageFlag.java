package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.*;

public enum VulkanicImageUsageFlag implements Bitmaskable<Integer> {
    TRANSFER_SRC(VK10.VK_IMAGE_USAGE_TRANSFER_SRC_BIT),
    TRANSFER_DST(VK10.VK_IMAGE_USAGE_TRANSFER_DST_BIT),
    SAMPLED(VK10.VK_IMAGE_USAGE_SAMPLED_BIT),
    STORAGE(VK10.VK_IMAGE_USAGE_STORAGE_BIT),
    COLOR_ATTACHMENT(VK10.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT),
    DEPTH_STENCIL_ATTACHMENT(VK10.VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT),
    TRANSIENT_ATTACHMENT(VK10.VK_IMAGE_USAGE_TRANSIENT_ATTACHMENT_BIT),
    INPUT_ATTACHMENT(VK10.VK_IMAGE_USAGE_INPUT_ATTACHMENT_BIT),
    // Provided by VK_VERSION_1_4
    HOST_TRANSFER(VK14.VK_IMAGE_USAGE_HOST_TRANSFER_BIT),
    // Provided by VK_KHR_video_decode_queue
    VIDEO_DECODE_DST_KHR(KHRVideoDecodeQueue.VK_IMAGE_USAGE_VIDEO_DECODE_DST_BIT_KHR),
    // Provided by VK_KHR_video_decode_queue
    VIDEO_DECODE_SRC_KHR(KHRVideoDecodeQueue.VK_IMAGE_USAGE_VIDEO_DECODE_SRC_BIT_KHR),
    // Provided by VK_KHR_video_decode_queue
    VIDEO_DECODE_DPB_KHR(KHRVideoDecodeQueue.VK_IMAGE_USAGE_VIDEO_DECODE_DPB_BIT_KHR),
    // Provided by VK_EXT_fragment_density_map
    FRAGMENT_DENSITY_MAP_EXT(EXTFragmentDensityMap.VK_IMAGE_USAGE_FRAGMENT_DENSITY_MAP_BIT_EXT),
    // Provided by VK_KHR_fragment_shading_rate
    FRAGMENT_SHADING_RATE_ATTACHMENT_KHR(KHRFragmentShadingRate.VK_IMAGE_USAGE_FRAGMENT_SHADING_RATE_ATTACHMENT_BIT_KHR),
    // Provided by VK_KHR_video_encode_queue
    VIDEO_ENCODE_DST_KHR(KHRVideoEncodeQueue.VK_IMAGE_USAGE_VIDEO_ENCODE_DST_BIT_KHR),
    // Provided by VK_KHR_video_encode_queue
    VIDEO_ENCODE_SRC_KHR(KHRVideoEncodeQueue.VK_IMAGE_USAGE_VIDEO_ENCODE_SRC_BIT_KHR),
    // Provided by VK_KHR_video_encode_queue
    VIDEO_ENCODE_DPB_KHR(KHRVideoEncodeQueue.VK_IMAGE_USAGE_VIDEO_ENCODE_DPB_BIT_KHR),
    // Provided by VK_EXT_attachment_feedback_loop_layout
    ATTACHMENT_FEEDBACK_LOOP_EXT(EXTAttachmentFeedbackLoopLayout.VK_IMAGE_USAGE_ATTACHMENT_FEEDBACK_LOOP_BIT_EXT),
    // Provided by VK_HUAWEI_invocation_mask
    INVOCATION_MASK_HUAWEI(HUAWEIInvocationMask.VK_IMAGE_USAGE_INVOCATION_MASK_BIT_HUAWEI),
    // Provided by VK_QCOM_image_processing
    SAMPLE_WEIGHT_QCOM(QCOMImageProcessing.VK_IMAGE_USAGE_SAMPLE_WEIGHT_BIT_QCOM),
    // Provided by VK_QCOM_image_processing
    SAMPLE_BLOCK_MATCH_QCOM(QCOMImageProcessing.VK_IMAGE_USAGE_SAMPLE_BLOCK_MATCH_BIT_QCOM),
    // Provided by VK_ARM_tensors
    TENSOR_ALIASING_ARM(ARMTensors.VK_IMAGE_USAGE_TENSOR_ALIASING_BIT_ARM),
    // Provided by VK_QCOM_tile_memory_heap
    TILE_MEMORY_QCOM(QCOMTileMemoryHeap.VK_IMAGE_USAGE_TILE_MEMORY_BIT_QCOM),
    // Provided by VK_KHR_video_encode_quantization_map
    VIDEO_ENCODE_QUANTIZATION_DELTA_MAP_KHR(KHRVideoEncodeQuantizationMap.VK_IMAGE_USAGE_VIDEO_ENCODE_QUANTIZATION_DELTA_MAP_BIT_KHR),
    // Provided by VK_KHR_video_encode_quantization_map
    VIDEO_ENCODE_EMPHASIS_MAP_KHR(KHRVideoEncodeQuantizationMap.VK_IMAGE_USAGE_VIDEO_ENCODE_EMPHASIS_MAP_BIT_KHR),
    // Provided by VK_NV_shading_rate_image
    SHADING_RATE_IMAGE_NV(FRAGMENT_SHADING_RATE_ATTACHMENT_KHR),
    // Provided by VK_EXT_host_image_copy
    HOST_TRANSFER_EXT(HOST_TRANSFER),

    ;

    private final int flag;
    VulkanicImageUsageFlag(VulkanicImageUsageFlag alias) {
        this.flag = alias.flag();
    }
    VulkanicImageUsageFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
