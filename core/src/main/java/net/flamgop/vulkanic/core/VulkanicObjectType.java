package net.flamgop.vulkanic.core;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.*;

import java.util.HashMap;
import java.util.Map;

public enum VulkanicObjectType {
    /// An unknown object (possibly unassociated or third-party)
    UNKNOWN(VK10.VK_OBJECT_TYPE_UNKNOWN),
    /// A VkInstance object
    INSTANCE(VK10.VK_OBJECT_TYPE_INSTANCE),
    /// A VkPhysicalDevice object
    PHYSICAL_DEVICE(VK10.VK_OBJECT_TYPE_PHYSICAL_DEVICE),
    /// A VkDevice object
    DEVICE(VK10.VK_OBJECT_TYPE_DEVICE),
    /// A VkQueue object
    QUEUE(VK10.VK_OBJECT_TYPE_QUEUE),
    /// A VkSemaphore object
    SEMAPHORE(VK10.VK_OBJECT_TYPE_SEMAPHORE),
    /// A VkCommandBuffer object
    COMMAND_BUFFER(VK10.VK_OBJECT_TYPE_COMMAND_BUFFER),
    /// A VkFence object
    FENCE(VK10.VK_OBJECT_TYPE_FENCE),
    /// A VkDeviceMemory object
    DEVICE_MEMORY(VK10.VK_OBJECT_TYPE_DEVICE_MEMORY),
    /// A VkBuffer object
    BUFFER(VK10.VK_OBJECT_TYPE_BUFFER),
    /// A VkImage object
    IMAGE(VK10.VK_OBJECT_TYPE_IMAGE),
    /// A VkEvent object
    EVENT(VK10.VK_OBJECT_TYPE_EVENT),
    /// A VkQueryPool object
    QUERY_POOL(VK10.VK_OBJECT_TYPE_QUERY_POOL),
    /// A VkBufferView object
    BUFFER_VIEW(VK10.VK_OBJECT_TYPE_BUFFER_VIEW),
    /// A VkImageView object
    IMAGE_VIEW(VK10.VK_OBJECT_TYPE_IMAGE_VIEW),
    /// A VkShaderModule object
    SHADER_MODULE(VK10.VK_OBJECT_TYPE_SHADER_MODULE),
    /// A VkPipelineCache object
    PIPELINE_CACHE(VK10.VK_OBJECT_TYPE_PIPELINE_CACHE),
    /// A VkPipelineLayout object
    PIPELINE_LAYOUT(VK10.VK_OBJECT_TYPE_PIPELINE_LAYOUT),
    /// A VkRenderPass object
    RENDER_PASS(VK10.VK_OBJECT_TYPE_RENDER_PASS),
    /// A VkPipeline object
    PIPELINE(VK10.VK_OBJECT_TYPE_PIPELINE),
    /// A VkDescriptorSetLayout object
    DESCRIPTOR_SET_LAYOUT(VK10.VK_OBJECT_TYPE_DESCRIPTOR_SET_LAYOUT),
    /// A VkSampler object
    SAMPLER(VK10.VK_OBJECT_TYPE_SAMPLER),
    /// A VkDescriptorPool object
    DESCRIPTOR_POOL(VK10.VK_OBJECT_TYPE_DESCRIPTOR_POOL),
    /// A VkDescriptorSet object
    DESCRIPTOR_SET(VK10.VK_OBJECT_TYPE_DESCRIPTOR_SET),
    /// A VkFramebuffer object
    FRAMEBUFFER(VK10.VK_OBJECT_TYPE_FRAMEBUFFER),
    /// A VkCommandPool object
    COMMAND_POOL(VK10.VK_OBJECT_TYPE_COMMAND_POOL),

    /// A VkDescriptorUpdateTemplate object
    DESCRIPTOR_UPDATE_TEMPLATE(VK11.VK_OBJECT_TYPE_DESCRIPTOR_UPDATE_TEMPLATE),
    /// A VkSamplerYCBCRConversion object
    SAMPLER_YCBCR_CONVERSION(VK11.VK_OBJECT_TYPE_SAMPLER_YCBCR_CONVERSION),

    /// A VkPrivateDataSlot object
    PRIVATE_DATA_SLOT(VK13.VK_OBJECT_TYPE_PRIVATE_DATA_SLOT),

    /// A VkSurfaceKHR object
    SURFACE_KHR(KHRSurface.VK_OBJECT_TYPE_SURFACE_KHR),

    /// A VkSwapchainKHR object
    SWAPCHAIN_KHR(KHRSwapchain.VK_OBJECT_TYPE_SWAPCHAIN_KHR),

    /// A VkDisplayKHR object
    DISPLAY_KHR(KHRDisplay.VK_OBJECT_TYPE_DISPLAY_KHR),
    /// A VkDisplayModeKHR object
    DISPLAY_MODE_KHR(KHRDisplay.VK_OBJECT_TYPE_DISPLAY_MODE_KHR),

    /// A VkDebugReportCallbackEXT object
    DEBUG_REPORT_CALLBACK_EXT(EXTDebugReport.VK_DEBUG_REPORT_OBJECT_TYPE_DEBUG_REPORT_CALLBACK_EXT_EXT),

    /// A VkVideoSessionKHR object
    VIDEO_SESSION_KHR(KHRVideoQueue.VK_OBJECT_TYPE_VIDEO_SESSION_KHR),
    /// A VkVideoSessionParametersKHR object
    VIDEO_SESSION_PARAMETERS_KHR(KHRVideoQueue.VK_OBJECT_TYPE_VIDEO_SESSION_PARAMETERS_KHR),

    /// A VkCuModuleNVX object
    CU_MODULE_NVX(NVXBinaryImport.VK_OBJECT_TYPE_CU_MODULE_NVX),
    /// A VkCuFunctionNVX object
    CU_FUNCTION_NVX(NVXBinaryImport.VK_OBJECT_TYPE_CU_FUNCTION_NVX),

    /// A VkDebugUtilsMessengerEXT object
    DEBUG_UTILS_MESSENGER_EXT(EXTDebugUtils.VK_OBJECT_TYPE_DEBUG_UTILS_MESSENGER_EXT),

    /// A VkAccelerationStructureKHR object
    ACCELERATION_STRUCTURE_KHR(KHRAccelerationStructure.VK_OBJECT_TYPE_ACCELERATION_STRUCTURE_KHR),

    /// A VkValidationCacheEXT object
    VALIDATION_CACHE_EXT(EXTValidationCache.VK_OBJECT_TYPE_VALIDATION_CACHE_EXT),

    /// A VkAccelerationStructureNV object
    ACCELERATION_STRUCTURE_NV(NVRayTracing.VK_OBJECT_TYPE_ACCELERATION_STRUCTURE_NV),

    /// A VkPerformanceConfigurationINTEL object
    PERFORMANCE_CONFIGURATION_INTEL(INTELPerformanceQuery.VK_OBJECT_TYPE_PERFORMANCE_CONFIGURATION_INTEL),

    /// A VkDeferredOperationKHR object
    DEFERRED_OPERATION_KHR(KHRDeferredHostOperations.VK_OBJECT_TYPE_DEFERRED_OPERATION_KHR),

    /// A VkIndirectCommandsLayoutNV object
    INDIRECT_COMMANDS_LAYOUT_NV(NVDeviceGeneratedCommands.VK_OBJECT_TYPE_INDIRECT_COMMANDS_LAYOUT_NV),

    /// A VkCudaKernelLaunchNV object
    CUDA_MODULE_NV(NVCudaKernelLaunch.VK_OBJECT_TYPE_CUDA_MODULE_NV),
    /// A VkCudaKernelFunctionNV object
    CUDA_FUNCTION_NV(NVCudaKernelLaunch.VK_OBJECT_TYPE_CUDA_FUNCTION_NV),

//    /// A VkBufferCollectionFUCHSIA object
//    BUFFER_COLLECTION_FUCHSIA(FUCHSIABufferCollection.VK_OBJECT_TYPE_BUFFER_COLLECTION_FUCHSIA), // ???

    /// A VkMicromapEXT object
    MICROMAP_EXT(EXTOpacityMicromap.VK_OBJECT_TYPE_MICROMAP_EXT),

    /// A VkTensorARM object
    TENSOR_ARM(ARMTensors.VK_OBJECT_TYPE_TENSOR_ARM),
    /// A VkTensorViewARM object
    TENSOR_VIEW_ARM(ARMTensors.VK_OBJECT_TYPE_TENSOR_VIEW_ARM),

    /// A VkOpticalFlowSessionNV object
    OPTICAL_FLOW_SESSION_NV(NVOpticalFlow.VK_OBJECT_TYPE_OPTICAL_FLOW_SESSION_NV),

    /// A VkShaderEXT object
    SHADER_EXT(EXTShaderObject.VK_OBJECT_TYPE_SHADER_EXT),

    /// A VkPipelineBinaryKHR object
    PIPELINE_BINARY_KHR(KHRPipelineBinary.VK_OBJECT_TYPE_PIPELINE_BINARY_KHR),

    /// A VkDataGraphPipelineSessionARM object
    DATA_GRAPH_PIPELINE_SESSION_ARM(ARMDataGraph.VK_OBJECT_TYPE_DATA_GRAPH_PIPELINE_SESSION_ARM),

    /// A VkExternalComputeQueueNV object
    EXTERNAL_COMPUTE_QUEUE_NV(NVExternalComputeQueue.VK_OBJECT_TYPE_EXTERNAL_COMPUTE_QUEUE_NV),

    /// A VkIndirectCommandsLayoutEXT object
    INDIRECT_COMMANDS_LAYOUT_EXT(EXTDeviceGeneratedCommands.VK_OBJECT_TYPE_INDIRECT_COMMANDS_LAYOUT_EXT),
    /// A VkIndirectExecutionSetEXT object
    INDIRECT_EXECUTION_SET_EXT(EXTDeviceGeneratedCommands.VK_OBJECT_TYPE_INDIRECT_EXECUTION_SET_EXT),

//    /// A VkShaderInstrumentationARM object
//    SHADER_INSTRUMENTATION_ARM(ARMShaderInstrumentation.VK_OBJECT_TYPE_SHADER_INSTRUMENTATION_ARM), // ??

    ;
    private static final Map<Integer, VulkanicObjectType> LOOKUP = new HashMap<>();
    static {
        for (VulkanicObjectType type : values()) {
            LOOKUP.put(type.qualifier, type);
        }
    }

    private final int qualifier;
    VulkanicObjectType(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }

    public static @NotNull VulkanicObjectType valueOf(int value) {
        return LOOKUP.getOrDefault(value, UNKNOWN);
    }
}
