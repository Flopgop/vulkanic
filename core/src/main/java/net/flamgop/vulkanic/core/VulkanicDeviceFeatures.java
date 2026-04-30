package net.flamgop.vulkanic.core;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.NativeResource;
import org.lwjgl.vulkan.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.*;

public class VulkanicDeviceFeatures implements AutoCloseable {

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

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private final Set<String> extensions = new HashSet<>();
    private final List<NativeResource> pNextChain = new ArrayList<>();

    private boolean synchronization2 = false;
    private boolean bufferDeviceAddress = false;
    private boolean dynamicRendering = false;
    private boolean meshShader = false;
    private boolean taskShader = false;
    private boolean fillModeNonSolid = false;
    private boolean shaderInt64 = false;
    private boolean runtimeDescriptorArray = false;
    private boolean descriptorIndexing = false;
    private boolean shaderDrawParameters = false;
    private boolean multiDrawIndirect = false;
    private boolean hostQueryReset = false;
    private boolean shaderImageGatherExtended = false;
    private boolean shaderInt16 = false;
    private boolean storageBuffer16BitAccess = false;
    private boolean shaderInt8 = false;
    private boolean shaderFloat16 = false;
    private boolean storageBuffer8BitAccess = false;
    private boolean fullDrawIndexUint32 = false;
    private boolean imageCubeArray = false;
    private boolean independentBlend = false;
    private boolean sampleRateShading = false;
    private boolean drawIndirectFirstInstance = false;
    private boolean depthClamp = false;
    private boolean depthBiasClamp = false;
    private boolean samplerAnisotropy = false;
    private boolean occlusionQueryPrecise = false;
    private boolean fragmentStoresAndAtomics = false;
    private boolean shaderStorageImageExtendedFormats = false;
    private boolean shaderUniformBufferArrayDynamicIndexing = false;
    private boolean shaderSampledImageArrayDynamicIndexing = false;
    private boolean shaderStorageBufferArrayDynamicIndexing = false;
    private boolean shaderStorageImageArrayDynamicIndexing = false;
    private boolean samplerYcbcrConversion = false;
    private boolean samplerMirrorClampToEdge = false;
    private boolean shaderUniformTexelBufferArrayDynamicIndexing = false;
    private boolean shaderStorageTexelBufferArrayDynamicIndexing = false;
    private boolean shaderUniformBufferArrayNonUniformIndexing = false;
    private boolean shaderSampledImageArrayNonUniformIndexing = false;
    private boolean shaderStorageBufferArrayNonUniformIndexing = false;
    private boolean shaderStorageImageArrayNonUniformIndexing = false;
    private boolean shaderUniformTexelBufferArrayNonUniformIndexing = false;
    private boolean shaderStorageTexelBufferArrayNonUniformIndexing = false;
    private boolean descriptorBindingSampledImageUpdateAfterBind = false;
    private boolean descriptorBindingStorageImageUpdateAfterBind = false;
    private boolean descriptorBindingStorageBufferUpdateAfterBind = false;
    private boolean descriptorBindingUniformTexelBufferUpdateAfterBind = false;
    private boolean descriptorBindingStorageTexelBufferUpdateAfterBind = false;
    private boolean descriptorBindingUpdateUnusedWhilePending = false;
    private boolean descriptorBindingPartiallyBound = false;
    private boolean descriptorBindingVariableDescriptorCount = false;
    private boolean scalarBlockLayout = false;
    private boolean hostImageCopy = false;
    private boolean robustBufferAccess2 = false;
    private boolean robustImageAccess2 = false;
    private boolean nullDescriptor = false;
    private boolean descriptorHeap = false;
    private boolean descriptorHeapCaptureReplay = false;

    public boolean supportsSynchronization2() { return synchronization2; }
    public boolean supportsBufferDeviceAddress() { return bufferDeviceAddress; }
    public boolean supportsDynamicRendering() { return dynamicRendering; }
    public boolean supportsMeshShader() { return meshShader; }
    public boolean supportsTaskShader() { return taskShader; }
    public boolean supportsFillModeNonSolid() { return fillModeNonSolid; }
    public boolean supportsShaderInt64() { return shaderInt64; }
    public boolean supportsRuntimeDescriptorArray() { return runtimeDescriptorArray; }
    public boolean supportsDescriptorIndexing() { return descriptorIndexing; }
    public boolean supportsShaderDrawParameters() { return shaderDrawParameters; }
    public boolean supportsMultiDrawIndirect() { return multiDrawIndirect; }
    public boolean supportsHostQueryReset() { return hostQueryReset; }
    public boolean supportsShaderImageGatherExtended() { return shaderImageGatherExtended; }
    public boolean supportsShaderInt16() { return shaderInt16; }
    public boolean supportsStorageBuffer16BitAccess() { return storageBuffer16BitAccess; }
    public boolean supportsShaderInt8() { return shaderInt8; }
    public boolean supportsShaderFloat16() { return shaderFloat16; }
    public boolean supportsStorageBuffer8BitAccess() { return storageBuffer8BitAccess; }
    public boolean supportsFullDrawIndexUint32() { return fullDrawIndexUint32; }
    public boolean supportsImageCubeArray() { return imageCubeArray; }
    public boolean supportsIndependentBlend() { return independentBlend; }
    public boolean supportsSampleRateShading() { return sampleRateShading; }
    public boolean supportsDrawIndirectFirstInstance() { return drawIndirectFirstInstance; }
    public boolean supportsDepthClamp() { return depthClamp; }
    public boolean supportsDepthBiasClamp() { return depthBiasClamp; }
    public boolean supportsSamplerAnisotropy() { return samplerAnisotropy; }
    public boolean supportsOcclusionQueryPrecise() { return occlusionQueryPrecise; }
    public boolean supportsFragmentStoresAndAtomics() { return fragmentStoresAndAtomics; }
    public boolean supportsShaderStorageImageExtendedFormats() { return shaderStorageImageExtendedFormats; }
    public boolean supportsShaderUniformBufferArrayDynamicIndexing() { return shaderUniformBufferArrayDynamicIndexing; }
    public boolean supportsShaderSampledImageArrayDynamicIndexing() { return shaderSampledImageArrayDynamicIndexing; }
    public boolean supportsShaderStorageBufferArrayDynamicIndexing() { return shaderStorageBufferArrayDynamicIndexing; }
    public boolean supportsShaderStorageImageArrayDynamicIndexing() { return shaderStorageImageArrayDynamicIndexing; }
    public boolean supportsSamplerYcbcrConversion() { return samplerYcbcrConversion; }
    public boolean supportsSamplerMirrorClampToEdge() { return samplerMirrorClampToEdge; }
    public boolean supportsShaderUniformTexelBufferArrayDynamicIndexing() { return shaderUniformTexelBufferArrayDynamicIndexing; }
    public boolean supportsShaderStorageTexelBufferArrayDynamicIndexing() { return shaderStorageTexelBufferArrayDynamicIndexing; }
    public boolean supportsShaderUniformBufferArrayNonUniformIndexing() { return shaderUniformBufferArrayNonUniformIndexing; }
    public boolean supportsShaderSampledImageArrayNonUniformIndexing() { return shaderSampledImageArrayNonUniformIndexing; }
    public boolean supportsShaderStorageBufferArrayNonUniformIndexing() { return shaderStorageBufferArrayNonUniformIndexing; }
    public boolean supportsShaderStorageImageArrayNonUniformIndexing() { return shaderStorageImageArrayNonUniformIndexing; }
    public boolean supportsShaderUniformTexelBufferArrayNonUniformIndexing() { return shaderUniformTexelBufferArrayNonUniformIndexing; }
    public boolean supportsShaderStorageTexelBufferArrayNonUniformIndexing() { return shaderStorageTexelBufferArrayNonUniformIndexing; }
    public boolean supportsDescriptorBindingSampledImageUpdateAfterBind() { return descriptorBindingSampledImageUpdateAfterBind; }
    public boolean supportsDescriptorBindingStorageImageUpdateAfterBind() { return descriptorBindingStorageImageUpdateAfterBind; }
    public boolean supportsDescriptorBindingStorageBufferUpdateAfterBind() { return descriptorBindingStorageBufferUpdateAfterBind; }
    public boolean supportsDescriptorBindingUniformTexelBufferUpdateAfterBind() { return descriptorBindingUniformTexelBufferUpdateAfterBind; }
    public boolean supportsDescriptorBindingStorageTexelBufferUpdateAfterBind() { return descriptorBindingStorageTexelBufferUpdateAfterBind; }
    public boolean supportsDescriptorBindingUpdateUnusedWhilePending() { return descriptorBindingUpdateUnusedWhilePending; }
    public boolean supportsDescriptorBindingPartiallyBound() { return descriptorBindingPartiallyBound; }
    public boolean supportsDescriptorBindingVariableDescriptorCount() { return descriptorBindingVariableDescriptorCount; }
    public boolean supportsScalarBlockLayout() { return scalarBlockLayout; }
    public boolean supportsHostImageCopy() { return hostImageCopy; }
    public boolean supportsRobustBufferAccess2() { return robustBufferAccess2; }
    public boolean supportsRobustImageAccess2() { return robustImageAccess2; }
    public boolean supportsNullDescriptor() { return nullDescriptor; }
    public boolean supportsDescriptorHeap() { return descriptorHeap; }
    public boolean supportsDescriptorHeapCaptureReplay() { return descriptorHeapCaptureReplay; }

    public VulkanicDeviceFeatures() {}

    private <T extends NativeResource> T getChainedPNext(Class<T> type) {
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

    public @NotNull VulkanicDeviceFeatures descriptorHeapCaptureReplay() {
        this.descriptorHeapCaptureReplay = true;
        this.extensions.add(EXTDescriptorHeap.VK_EXT_DESCRIPTOR_HEAP_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorHeapFeaturesEXT features = getChainedPNext(VkPhysicalDeviceDescriptorHeapFeaturesEXT.class);
        features.descriptorHeapCaptureReplay(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures descriptorHeap() {
        this.descriptorHeap = true;
        this.extensions.add(EXTDescriptorHeap.VK_EXT_DESCRIPTOR_HEAP_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorHeapFeaturesEXT features = getChainedPNext(VkPhysicalDeviceDescriptorHeapFeaturesEXT.class);
        features.descriptorHeap(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures nullDescriptor() {
        this.nullDescriptor = true;
        this.extensions.add(KHRRobustness2.VK_KHR_ROBUSTNESS_2_EXTENSION_NAME);
        VkPhysicalDeviceRobustness2FeaturesKHR features = getChainedPNext(VkPhysicalDeviceRobustness2FeaturesKHR.class);
        features.nullDescriptor(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures robustImageAccess2() {
        this.robustImageAccess2 = true;
        this.extensions.add(KHRRobustness2.VK_KHR_ROBUSTNESS_2_EXTENSION_NAME);
        VkPhysicalDeviceRobustness2FeaturesKHR features = getChainedPNext(VkPhysicalDeviceRobustness2FeaturesKHR.class);
        features.robustImageAccess2(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures robustBufferAccess2() {
        this.robustBufferAccess2 = true;
        this.extensions.add(KHRRobustness2.VK_KHR_ROBUSTNESS_2_EXTENSION_NAME);
        VkPhysicalDeviceRobustness2FeaturesKHR features = getChainedPNext(VkPhysicalDeviceRobustness2FeaturesKHR.class);
        features.robustBufferAccess2(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures hostImageCopy() {
        this.hostImageCopy = true;
        VkPhysicalDeviceHostImageCopyFeatures features = getChainedPNext(VkPhysicalDeviceHostImageCopyFeatures.class);
        features.hostImageCopy(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures occlusionQueryPrecise() {
        this.occlusionQueryPrecise = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().occlusionQueryPrecise(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures fragmentStoresAndAtomics() {
        this.fragmentStoresAndAtomics = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().fragmentStoresAndAtomics(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderStorageImageExtendedFormats() {
        this.shaderStorageImageExtendedFormats = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().shaderStorageImageExtendedFormats(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderUniformBufferArrayDynamicIndexing() {
        this.shaderUniformBufferArrayDynamicIndexing = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().shaderUniformBufferArrayDynamicIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderSampledImageArrayDynamicIndexing() {
        this.shaderSampledImageArrayDynamicIndexing = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().shaderSampledImageArrayDynamicIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderStorageBufferArrayDynamicIndexing() {
        this.shaderStorageBufferArrayDynamicIndexing = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().shaderStorageBufferArrayDynamicIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderStorageImageArrayDynamicIndexing() {
        this.shaderStorageImageArrayDynamicIndexing = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().shaderStorageImageArrayDynamicIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures samplerYcbcrConversion() {
        this.samplerYcbcrConversion = true;
        this.extensions.add(KHRSamplerYcbcrConversion.VK_KHR_SAMPLER_YCBCR_CONVERSION_EXTENSION_NAME);
        VkPhysicalDeviceSamplerYcbcrConversionFeatures features = getChainedPNext(VkPhysicalDeviceSamplerYcbcrConversionFeatures.class);
        features.samplerYcbcrConversion(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures samplerMirrorClampToEdge() {
        this.samplerMirrorClampToEdge = true;
        this.extensions.add(KHRSamplerMirrorClampToEdge.VK_KHR_SAMPLER_MIRROR_CLAMP_TO_EDGE_EXTENSION_NAME);
        VkPhysicalDeviceVulkan12Features features = getChainedPNext(VkPhysicalDeviceVulkan12Features.class);
        features.samplerMirrorClampToEdge(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderUniformTexelBufferArrayDynamicIndexing() {
        this.shaderUniformTexelBufferArrayDynamicIndexing = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.shaderUniformTexelBufferArrayDynamicIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderStorageTexelBufferArrayDynamicIndexing() {
        this.shaderStorageTexelBufferArrayDynamicIndexing = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.shaderStorageTexelBufferArrayDynamicIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderUniformBufferArrayNonUniformIndexing() {
        this.shaderUniformBufferArrayNonUniformIndexing = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.shaderUniformBufferArrayNonUniformIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderSampledImageArrayNonUniformIndexing() {
        this.shaderSampledImageArrayNonUniformIndexing = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.shaderSampledImageArrayNonUniformIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderStorageBufferArrayNonUniformIndexing() {
        this.shaderStorageBufferArrayNonUniformIndexing = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.shaderStorageBufferArrayNonUniformIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderStorageImageArrayNonUniformIndexing() {
        this.shaderStorageImageArrayNonUniformIndexing = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.shaderStorageImageArrayNonUniformIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderUniformTexelBufferArrayNonUniformIndexing() {
        this.shaderUniformTexelBufferArrayNonUniformIndexing = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.shaderUniformTexelBufferArrayNonUniformIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderStorageTexelBufferArrayNonUniformIndexing() {
        this.shaderStorageTexelBufferArrayNonUniformIndexing = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.shaderStorageTexelBufferArrayNonUniformIndexing(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures descriptorBindingSampledImageUpdateAfterBind() {
        this.descriptorBindingSampledImageUpdateAfterBind = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.descriptorBindingSampledImageUpdateAfterBind(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures descriptorBindingStorageImageUpdateAfterBind() {
        this.descriptorBindingStorageImageUpdateAfterBind = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.descriptorBindingStorageImageUpdateAfterBind(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures descriptorBindingStorageBufferUpdateAfterBind() {
        this.descriptorBindingStorageBufferUpdateAfterBind = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.descriptorBindingStorageBufferUpdateAfterBind(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures descriptorBindingUniformTexelBufferUpdateAfterBind() {
        this.descriptorBindingUniformTexelBufferUpdateAfterBind = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.descriptorBindingUniformTexelBufferUpdateAfterBind(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures descriptorBindingStorageTexelBufferUpdateAfterBind() {
        this.descriptorBindingStorageTexelBufferUpdateAfterBind = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.descriptorBindingStorageTexelBufferUpdateAfterBind(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures descriptorBindingUpdateUnusedWhilePending() {
        this.descriptorBindingUpdateUnusedWhilePending = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.descriptorBindingUpdateUnusedWhilePending(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures descriptorBindingPartiallyBound() {
        this.descriptorBindingPartiallyBound = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.descriptorBindingPartiallyBound(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures descriptorBindingVariableDescriptorCount() {
        this.descriptorBindingVariableDescriptorCount = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures features = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        features.descriptorBindingVariableDescriptorCount(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures scalarBlockLayout() {
        this.scalarBlockLayout = true;
        this.extensions.add(EXTScalarBlockLayout.VK_EXT_SCALAR_BLOCK_LAYOUT_EXTENSION_NAME);
        VkPhysicalDeviceScalarBlockLayoutFeatures features = getChainedPNext(VkPhysicalDeviceScalarBlockLayoutFeatures.class);
        features.scalarBlockLayout(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures samplerAnisotropy() {
        this.samplerAnisotropy = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().samplerAnisotropy(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures depthBiasClamp() {
        this.depthBiasClamp = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().depthBiasClamp(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures depthClamp() {
        this.depthClamp = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().depthClamp(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures drawIndirectFirstInstance() {
        this.drawIndirectFirstInstance = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().drawIndirectFirstInstance(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures sampleRateShading() {
        this.sampleRateShading = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().sampleRateShading(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures independentBlend() {
        this.independentBlend = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().independentBlend(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures imageCubeArray() {
        this.imageCubeArray = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().imageCubeArray(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures fullDrawIndexUint32() {
        this.fullDrawIndexUint32 = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().fullDrawIndexUint32(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures storageBuffer8BitAccess() {
        this.storageBuffer8BitAccess = true;
        this.extensions.add(KHR8bitStorage.VK_KHR_8BIT_STORAGE_EXTENSION_NAME);
        VkPhysicalDevice8BitStorageFeatures features = getChainedPNext(VkPhysicalDevice8BitStorageFeatures.class);
        features.storageBuffer8BitAccess(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderFloat16() {
        this.shaderFloat16 = true;
        this.extensions.add(KHRShaderFloat16Int8.VK_KHR_SHADER_FLOAT16_INT8_EXTENSION_NAME);
        VkPhysicalDeviceShaderFloat16Int8Features features = getChainedPNext(VkPhysicalDeviceShaderFloat16Int8Features.class);
        features.shaderFloat16(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderInt8() {
        this.shaderInt8 = true;
        this.extensions.add(KHRShaderFloat16Int8.VK_KHR_SHADER_FLOAT16_INT8_EXTENSION_NAME);
        VkPhysicalDeviceShaderFloat16Int8Features features = getChainedPNext(VkPhysicalDeviceShaderFloat16Int8Features.class);
        features.shaderInt8(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures storageBuffer16BitAccess() {
        this.storageBuffer16BitAccess = true;
        this.extensions.add(KHR16bitStorage.VK_KHR_16BIT_STORAGE_EXTENSION_NAME);
        VkPhysicalDevice16BitStorageFeatures features = getChainedPNext(VkPhysicalDevice16BitStorageFeatures.class);
        features.storageBuffer16BitAccess(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderInt16() {
        this.shaderInt16 = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().shaderInt16(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderImageGatherExtended() {
        this.shaderImageGatherExtended = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().shaderImageGatherExtended(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures multiDrawIndirect() {
        this.multiDrawIndirect = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().multiDrawIndirect(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures synchronization2() {
        this.synchronization2 = true;
        this.extensions.add(KHRSynchronization2.VK_KHR_SYNCHRONIZATION_2_EXTENSION_NAME);
        VkPhysicalDeviceSynchronization2Features sync2Features = getChainedPNext(VkPhysicalDeviceSynchronization2Features.class);
        sync2Features.synchronization2(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures bufferDeviceAddress() {
        this.bufferDeviceAddress = true;
        this.extensions.add(KHRBufferDeviceAddress.VK_KHR_BUFFER_DEVICE_ADDRESS_EXTENSION_NAME);
        VkPhysicalDeviceBufferDeviceAddressFeatures bdaFeatures = getChainedPNext(VkPhysicalDeviceBufferDeviceAddressFeatures.class);
        bdaFeatures.bufferDeviceAddress(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures dynamicRendering() {
        this.dynamicRendering = true;
        this.extensions.add(KHRDynamicRendering.VK_KHR_DYNAMIC_RENDERING_EXTENSION_NAME);
        VkPhysicalDeviceDynamicRenderingFeatures dynamicRenderingFeatures = getChainedPNext(VkPhysicalDeviceDynamicRenderingFeatures.class);
        dynamicRenderingFeatures.dynamicRendering(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures meshShader() {
        this.meshShader = true;
        this.extensions.add(EXTMeshShader.VK_EXT_MESH_SHADER_EXTENSION_NAME);
        VkPhysicalDeviceMeshShaderFeaturesEXT meshFeatures = getChainedPNext(VkPhysicalDeviceMeshShaderFeaturesEXT.class);
        meshFeatures.meshShader(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures taskShader() {
        this.taskShader = true;
        this.extensions.add(EXTMeshShader.VK_EXT_MESH_SHADER_EXTENSION_NAME);
        VkPhysicalDeviceMeshShaderFeaturesEXT meshFeatures = getChainedPNext(VkPhysicalDeviceMeshShaderFeaturesEXT.class);
        meshFeatures.taskShader(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures fillModeNonSolid() {
        this.fillModeNonSolid = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().fillModeNonSolid(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderInt64() {
        this.shaderInt64 = true;
        VkPhysicalDeviceFeatures2 features = getChainedPNext(VkPhysicalDeviceFeatures2.class);
        features.features().shaderInt64(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures runtimeDescriptorArray() {
        this.runtimeDescriptorArray = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures descriptorIndexingFeatures = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        descriptorIndexingFeatures.runtimeDescriptorArray(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures descriptorIndexing() {
        this.descriptorIndexing = true;
        this.extensions.add(EXTDescriptorIndexing.VK_EXT_DESCRIPTOR_INDEXING_EXTENSION_NAME);
        VkPhysicalDeviceDescriptorIndexingFeatures descriptorIndexingFeatures = getChainedPNext(VkPhysicalDeviceDescriptorIndexingFeatures.class);
        descriptorIndexingFeatures.descriptorBindingPartiallyBound(true);
        descriptorIndexingFeatures.descriptorBindingSampledImageUpdateAfterBind(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures shaderDrawParameters() {
        this.shaderDrawParameters = true;
        this.extensions.add(KHRShaderDrawParameters.VK_KHR_SHADER_DRAW_PARAMETERS_EXTENSION_NAME);
        VkPhysicalDeviceShaderDrawParametersFeatures drawParametersFeatures = getChainedPNext(VkPhysicalDeviceShaderDrawParametersFeatures.class);
        drawParametersFeatures.shaderDrawParameters(true);
        return this;
    }

    public @NotNull VulkanicDeviceFeatures hostQueryReset() {
        this.hostQueryReset = true;
        this.extensions.add(EXTHostQueryReset.VK_EXT_HOST_QUERY_RESET_EXTENSION_NAME);
        VkPhysicalDeviceHostQueryResetFeatures hostQueryResetFeatures = getChainedPNext(VkPhysicalDeviceHostQueryResetFeatures.class);
        hostQueryResetFeatures.hostQueryReset(true);
        return this;
    }

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
}
