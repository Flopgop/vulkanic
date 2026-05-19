package net.flamgop.vulkanic.core.feature;

import net.flamgop.vulkanic.annotations.VulkanExtension;
import net.flamgop.vulkanic.annotations.VulkanFeature;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.NativeResource;
import org.lwjgl.vulkan.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.*;

// TODO: this still isn't very good, but it functions
/// @apiNote !! This must be closed after it is used !!
public class VulkanicDeviceFeatures extends VulkanicFeaturesGenerated implements AutoCloseable {

    public static VulkanicDeviceFeatures roadmap2022() {
        VulkanicDeviceFeatures features = new VulkanicDeviceFeatures()
                .fullDrawIndexUint32()
                .imageCubeArray()
                .independentBlend()
                .sampleRateShading()
                .drawIndirectFirstInstance()
                .depthClamp()
                .depthBiasClamp()
                .samplerAnisotropy()
                .occlusionQueryPrecise()
                .fragmentStoresAndAtomics()
                .shaderStorageImageExtendedFormats()
                .shaderUniformBufferArrayDynamicIndexing()
                .shaderSampledImageArrayDynamicIndexing()
                .shaderStorageBufferArrayDynamicIndexing()
                .shaderStorageImageArrayDynamicIndexing()
                .samplerYcbcrConversion()
                .samplerMirrorClampToEdge()
                .descriptorIndexing()
                .shaderUniformTexelBufferArrayDynamicIndexing()
                .shaderStorageTexelBufferArrayDynamicIndexing()
                .shaderUniformBufferArrayNonUniformIndexing()
                .shaderSampledImageArrayNonUniformIndexing()
                .shaderStorageBufferArrayNonUniformIndexing()
                .shaderStorageImageArrayNonUniformIndexing()
                .shaderUniformTexelBufferArrayNonUniformIndexing()
                .shaderStorageTexelBufferArrayNonUniformIndexing()
                .descriptorBindingSampledImageUpdateAfterBind()
                .descriptorBindingStorageImageUpdateAfterBind()
                .descriptorBindingStorageBufferUpdateAfterBind()
                .descriptorBindingUniformTexelBufferUpdateAfterBind()
                .descriptorBindingStorageTexelBufferUpdateAfterBind()
                .descriptorBindingUpdateUnusedWhilePending()
                .descriptorBindingPartiallyBound()
                .descriptorBindingVariableDescriptorCount()
                .runtimeDescriptorArray()
                .scalarBlockLayout();

        features.extensions.add(KHRGlobalPriority.VK_KHR_GLOBAL_PRIORITY_EXTENSION_NAME);

        return features;
    }

    public static VulkanicDeviceFeatures roadmap2024() {
        VulkanicDeviceFeatures features = roadmap2022()
                .multiDrawIndirect()
                .shaderImageGatherExtended()
                .shaderInt16()
                .shaderDrawParameters()
                .storageBuffer16BitAccess()
                .shaderInt8()
                .shaderFloat16()
                .storageBuffer8BitAccess();

        features.extensions.addAll(
                List.of(
                        KHRDynamicRenderingLocalRead.VK_KHR_DYNAMIC_RENDERING_LOCAL_READ_EXTENSION_NAME,
                        KHRLoadStoreOpNone.VK_KHR_LOAD_STORE_OP_NONE_EXTENSION_NAME,
                        KHRShaderQuadControl.VK_KHR_SHADER_QUAD_CONTROL_EXTENSION_NAME,
                        KHRShaderMaximalReconvergence.VK_KHR_SHADER_MAXIMAL_RECONVERGENCE_EXTENSION_NAME,
                        KHRShaderSubgroupUniformControlFlow.VK_KHR_SHADER_SUBGROUP_UNIFORM_CONTROL_FLOW_EXTENSION_NAME,
                        KHRShaderSubgroupRotate.VK_KHR_SHADER_SUBGROUP_ROTATE_EXTENSION_NAME,
                        KHRShaderFloatControls2.VK_KHR_SHADER_FLOAT_CONTROLS_2_EXTENSION_NAME,
                        KHRShaderExpectAssume.VK_KHR_SHADER_EXPECT_ASSUME_EXTENSION_NAME,
                        KHRLineRasterization.VK_KHR_LINE_RASTERIZATION_EXTENSION_NAME,
                        KHRVertexAttributeDivisor.VK_KHR_VERTEX_ATTRIBUTE_DIVISOR_EXTENSION_NAME,
                        KHRIndexTypeUint8.VK_KHR_INDEX_TYPE_UINT8_EXTENSION_NAME,
                        KHRMapMemory2.VK_KHR_MAP_MEMORY_2_EXTENSION_NAME,
                        KHRMaintenance5.VK_KHR_MAINTENANCE_5_EXTENSION_NAME,
                        KHRPushDescriptor.VK_KHR_PUSH_DESCRIPTOR_EXTENSION_NAME
                )
        );

        return features;
    }

    public static VulkanicDeviceFeatures roadmap2026() {
        VulkanicDeviceFeatures features = roadmap2024()
                .hostImageCopy()
                .robustBufferAccess2()
                .robustImageAccess2()
                .nullDescriptor();

        features.extensions.addAll(List.of(
                KHRRobustness2.VK_KHR_ROBUSTNESS_2_EXTENSION_NAME,
                KHRPipelineBinary.VK_KHR_PIPELINE_BINARY_EXTENSION_NAME,
                KHRFragmentShadingRate.VK_KHR_FRAGMENT_SHADING_RATE_EXTENSION_NAME,
                KHRShaderClock.VK_KHR_SHADER_CLOCK_EXTENSION_NAME,
                KHRWorkgroupMemoryExplicitLayout.VK_KHR_WORKGROUP_MEMORY_EXPLICIT_LAYOUT_EXTENSION_NAME,
                KHRComputeShaderDerivatives.VK_KHR_COMPUTE_SHADER_DERIVATIVES_EXTENSION_NAME,
                KHRMaintenance7.VK_KHR_MAINTENANCE_7_EXTENSION_NAME,
                KHRMaintenance8.VK_KHR_MAINTENANCE_8_EXTENSION_NAME,
                KHRMaintenance9.VK_KHR_MAINTENANCE_9_EXTENSION_NAME,
                KHRDepthClampZeroOne.VK_KHR_DEPTH_CLAMP_ZERO_ONE_EXTENSION_NAME,
                KHRCopyMemoryIndirect.VK_KHR_COPY_MEMORY_INDIRECT_EXTENSION_NAME,
                KHRShaderUntypedPointers.VK_KHR_SHADER_UNTYPED_POINTERS_EXTENSION_NAME,
                KHRPresentModeFifoLatestReady.VK_KHR_PRESENT_MODE_FIFO_LATEST_READY_EXTENSION_NAME,
                KHRPresentId2.VK_KHR_PRESENT_ID_2_EXTENSION_NAME,
                KHRPresentWait2.VK_KHR_PRESENT_WAIT_2_EXTENSION_NAME,
                KHRSurfaceMaintenance1.VK_KHR_SURFACE_MAINTENANCE_1_EXTENSION_NAME,
                KHRSwapchainMaintenance1.VK_KHR_SWAPCHAIN_MAINTENANCE_1_EXTENSION_NAME,
                KHRCooperativeMatrix.VK_KHR_COOPERATIVE_MATRIX_EXTENSION_NAME
        ));

        return features;
    }

    @VulkanFeature(extension = KHRPresentModeFifoLatestReady.VK_KHR_PRESENT_MODE_FIFO_LATEST_READY_EXTENSION_NAME, featuresStruct = VkPhysicalDevicePresentModeFifoLatestReadyFeaturesKHR.class)
    private static final boolean presentModeFifoLatestReady = false;
    @VulkanFeature(extension = KHRSynchronization2.VK_KHR_SYNCHRONIZATION_2_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceSynchronization2Features.class)
    private static final boolean synchronization2 = false;
    @VulkanFeature(extension = KHRBufferDeviceAddress.VK_KHR_BUFFER_DEVICE_ADDRESS_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceBufferDeviceAddressFeatures.class)
    private static final boolean bufferDeviceAddress = false;
    @VulkanFeature(extension = KHRDynamicRendering.VK_KHR_DYNAMIC_RENDERING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDynamicRenderingFeatures.class)
    private static final boolean dynamicRendering = false;
    @VulkanFeature(extension = EXTMeshShader.VK_EXT_MESH_SHADER_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceMeshShaderFeaturesEXT.class)
    private static final boolean meshShader = false;
    @VulkanFeature(extension = EXTMeshShader.VK_EXT_MESH_SHADER_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceMeshShaderFeaturesEXT.class)
    private static final boolean taskShader = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean fillModeNonSolid = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean shaderInt64 = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean runtimeDescriptorArray = false;
    @VulkanExtension(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME)
    private static final boolean descriptorIndexing = false;
    @VulkanFeature(extension = KHRShaderDrawParameters.VK_KHR_SHADER_DRAW_PARAMETERS_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceShaderDrawParametersFeatures.class)
    private static final boolean shaderDrawParameters = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean multiDrawIndirect = false;
    @VulkanFeature(extension = EXTHostQueryReset.VK_EXT_HOST_QUERY_RESET_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceHostQueryResetFeatures.class)
    private static final boolean hostQueryReset = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean shaderImageGatherExtended = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean shaderInt16 = false;
    @VulkanFeature(extension = KHR16bitStorage.VK_KHR_16BIT_STORAGE_EXTENSION_NAME, featuresStruct = VkPhysicalDevice16BitStorageFeatures.class)
    private static final boolean storageBuffer16BitAccess = false;
    @VulkanFeature(extension = KHRShaderFloat16Int8.VK_KHR_SHADER_FLOAT16_INT8_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceShaderFloat16Int8Features.class)
    private static final boolean shaderInt8 = false;
    @VulkanFeature(extension = KHRShaderFloat16Int8.VK_KHR_SHADER_FLOAT16_INT8_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceShaderFloat16Int8Features.class)
    private static final boolean shaderFloat16 = false;
    @VulkanFeature(extension = KHR8bitStorage.VK_KHR_8BIT_STORAGE_EXTENSION_NAME, featuresStruct = VkPhysicalDevice8BitStorageFeatures.class)
    private static final boolean storageBuffer8BitAccess = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean fullDrawIndexUint32 = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean imageCubeArray = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean independentBlend = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean sampleRateShading = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean drawIndirectFirstInstance = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean depthClamp = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean depthBiasClamp = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean samplerAnisotropy = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean occlusionQueryPrecise = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean fragmentStoresAndAtomics = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean shaderStorageImageExtendedFormats = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean shaderUniformBufferArrayDynamicIndexing = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean shaderSampledImageArrayDynamicIndexing = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean shaderStorageBufferArrayDynamicIndexing = false;
    @VulkanFeature(featuresStruct = VkPhysicalDeviceFeatures2.class)
    private static final boolean shaderStorageImageArrayDynamicIndexing = false;
    @VulkanFeature(extension = KHRSamplerYcbcrConversion.VK_KHR_SAMPLER_YCBCR_CONVERSION_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceSamplerYcbcrConversionFeatures.class)
    private static final boolean samplerYcbcrConversion = false;
    @VulkanExtension(KHRSamplerMirrorClampToEdge.VK_KHR_SAMPLER_MIRROR_CLAMP_TO_EDGE_EXTENSION_NAME)
    private static final boolean samplerMirrorClampToEdge = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean shaderUniformTexelBufferArrayDynamicIndexing = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean shaderStorageTexelBufferArrayDynamicIndexing = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean shaderUniformBufferArrayNonUniformIndexing = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean shaderSampledImageArrayNonUniformIndexing = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean shaderStorageBufferArrayNonUniformIndexing = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean shaderStorageImageArrayNonUniformIndexing = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean shaderUniformTexelBufferArrayNonUniformIndexing = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean shaderStorageTexelBufferArrayNonUniformIndexing = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean descriptorBindingSampledImageUpdateAfterBind = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean descriptorBindingStorageImageUpdateAfterBind = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean descriptorBindingStorageBufferUpdateAfterBind = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean descriptorBindingUniformTexelBufferUpdateAfterBind = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean descriptorBindingStorageTexelBufferUpdateAfterBind = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean descriptorBindingUpdateUnusedWhilePending = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean descriptorBindingPartiallyBound = false;
    @VulkanFeature(extension = EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorIndexingFeatures.class)
    private static final boolean descriptorBindingVariableDescriptorCount = false;
    @VulkanFeature(extension = EXTScalarBlockLayout.VK_EXT_SCALAR_BLOCK_LAYOUT_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceScalarBlockLayoutFeatures.class)
    private static final boolean scalarBlockLayout = false;
    @VulkanFeature(extension = EXTHostImageCopy.VK_EXT_HOST_IMAGE_COPY_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceHostImageCopyFeatures.class)
    private static final boolean hostImageCopy = false;
    @VulkanFeature(extension = KHRRobustness2.VK_KHR_ROBUSTNESS_2_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceRobustness2FeaturesKHR.class)
    private static final boolean robustBufferAccess2 = false;
    @VulkanFeature(extension = KHRRobustness2.VK_KHR_ROBUSTNESS_2_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceRobustness2FeaturesKHR.class)
    private static final boolean robustImageAccess2 = false;
    @VulkanFeature(extension = KHRRobustness2.VK_KHR_ROBUSTNESS_2_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceRobustness2FeaturesKHR.class)
    private static final boolean nullDescriptor = false;
    @VulkanFeature(extension = EXTDescriptorHeap.VK_EXT_DESCRIPTOR_HEAP_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorHeapFeaturesEXT.class)
    private static final boolean descriptorHeap = false;
    @VulkanFeature(extension = EXTDescriptorHeap.VK_EXT_DESCRIPTOR_HEAP_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceDescriptorHeapFeaturesEXT.class)
    private static final boolean descriptorHeapCaptureReplay = false;
    @VulkanFeature(extension = KHRRayTracingPipeline.VK_KHR_RAY_TRACING_PIPELINE_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceRayTracingPipelineFeaturesKHR.class)
    private static final boolean rayTracingPipeline = false;
    @VulkanFeature(extension = KHRAccelerationStructure.VK_KHR_ACCELERATION_STRUCTURE_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceAccelerationStructureFeaturesKHR.class)
    private static final boolean accelerationStructure = false;
    @VulkanFeature(extension = EXTPresentTiming.VK_EXT_PRESENT_TIMING_EXTENSION_NAME, featuresStruct = VkPhysicalDevicePresentTimingFeaturesEXT.class)
    private static final boolean presentTiming = false;
    @VulkanFeature(extension = EXTSwapchainMaintenance1.VK_EXT_SWAPCHAIN_MAINTENANCE_1_EXTENSION_NAME, featuresStruct = VkPhysicalDeviceSwapchainMaintenance1FeaturesEXT.class)
    private static final boolean swapchainMaintenance1 = false;

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private final List<NativeResource> pNextChain = new ArrayList<>();

    public @NotNull Set<String> requiredExtensions() {
        return Collections.unmodifiableSet(this.extensions);
    }

    public long pNext() {
        if (pNextChain.isEmpty()) {
            return 0L;
        }
        try {
            for (int i = 0; i < pNextChain.size() - 1; i++) {
                NativeResource current = pNextChain.get(i);
                NativeResource next = pNextChain.get(i + 1);

                long nextAddress = (long) LOOKUP.findVirtual(next.getClass(), "address", MethodType.methodType(long.class))
                        .invoke(next);

                LOOKUP.findVirtual(current.getClass(), "pNext", MethodType.methodType(current.getClass(), long.class))
                        .invoke(current, nextAddress);
            }

            NativeResource last = pNextChain.getLast();
            LOOKUP.findVirtual(last.getClass(), "pNext", MethodType.methodType(last.getClass(), long.class))
                    .invoke(last, 0L);

            return (long) LOOKUP.findVirtual(pNextChain.getFirst().getClass(), "address", MethodType.methodType(long.class))
                    .invoke(pNextChain.getFirst());
        } catch (Throwable e) {
            throw new RuntimeException("Failed to link Vulkan pNext chain", e);
        }
    }

    @Override
    public void close() {
        pNextChain.forEach(NativeResource::close);
    }

    @Override
    protected <T extends NativeResource> T getChainedPNext(Class<T> type) {
        return pNextChain.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst()
                .orElseGet(() -> {
                    try {
                        @SuppressWarnings("unchecked")
                        T instance = (T) LOOKUP.findStatic(type, "calloc", MethodType.methodType(type)).invoke();
                        pNextChain.add(instance);
                        LOOKUP.findVirtual(type, "sType$Default", MethodType.methodType(type)).invoke(instance);
                        return instance;
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
