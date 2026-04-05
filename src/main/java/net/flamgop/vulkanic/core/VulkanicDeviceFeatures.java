package net.flamgop.vulkanic.core;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.NativeResource;
import org.lwjgl.vulkan.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.*;

public class VulkanicDeviceFeatures implements AutoCloseable {
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
