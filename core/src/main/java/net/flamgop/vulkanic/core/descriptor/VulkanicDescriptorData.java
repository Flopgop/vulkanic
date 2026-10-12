package net.flamgop.vulkanic.core.descriptor;

import net.flamgop.vulkanic.memory.image.sampler.VulkanicSampler;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkDescriptorAddressInfoEXT;
import org.lwjgl.vulkan.VkDescriptorDataEXT;
import org.lwjgl.vulkan.VkDescriptorImageInfo;

@SuppressWarnings("resource")
public sealed interface VulkanicDescriptorData {
    void get(VkDescriptorDataEXT pData);

    record Sampler(@NotNull VulkanicSampler sampler) implements VulkanicDescriptorData {
        @Override
        public void get(VkDescriptorDataEXT pData) {
            pData.pSampler(MemoryStack.stackGet().longs(sampler.handle()));
        }
    }

    record CombinedImageSampler(@NotNull VulkanicDescriptorImageInfo info) implements VulkanicDescriptorData {
        @Override
        public void get(VkDescriptorDataEXT pData) {
            pData.pCombinedImageSampler(
                    VkDescriptorImageInfo.calloc(MemoryStack.stackGet())
                            .sampler(info.sampler() != null ? info.sampler().handle() : 0)
                            .imageView(info.imageView() != null ? info.imageView().handle() : 0)
                            .imageLayout(info.imageLayout().qualifier())
            );
        }
    }

    record InputAttachmentImage(@NotNull VulkanicDescriptorImageInfo info) implements VulkanicDescriptorData {
        @Override
        public void get(VkDescriptorDataEXT pData) {
            pData.pInputAttachmentImage(
                    VkDescriptorImageInfo.calloc(MemoryStack.stackGet())
                            .sampler(info.sampler() != null ? info.sampler().handle() : 0)
                            .imageView(info.imageView() != null ? info.imageView().handle() : 0)
                            .imageLayout(info.imageLayout().qualifier())
            );
        }
    }

    record SampledImage(@NotNull VulkanicDescriptorImageInfo info) implements VulkanicDescriptorData {
        @Override
        public void get(VkDescriptorDataEXT pData) {
            pData.pSampledImage(
                    VkDescriptorImageInfo.calloc(MemoryStack.stackGet())
                            .sampler(info.sampler() != null ? info.sampler().handle() : 0)
                            .imageView(info.imageView() != null ? info.imageView().handle() : 0)
                            .imageLayout(info.imageLayout().qualifier())
            );
        }
    }

    record StorageImage(@NotNull VulkanicDescriptorImageInfo info) implements VulkanicDescriptorData {
        @Override
        public void get(VkDescriptorDataEXT pData) {
            pData.pStorageImage(
                    VkDescriptorImageInfo.calloc(MemoryStack.stackGet())
                            .sampler(info.sampler() != null ? info.sampler().handle() : 0)
                            .imageView(info.imageView() != null ? info.imageView().handle() : 0)
                            .imageLayout(info.imageLayout().qualifier())
            );
        }
    }

    record UniformTexelBuffer(@NotNull VulkanicDescriptorAddressInfo info) implements VulkanicDescriptorData {
        @Override
        public void get(VkDescriptorDataEXT pData) {
            pData.pUniformTexelBuffer(
                    VkDescriptorAddressInfoEXT.calloc(MemoryStack.stackGet())
                            .sType$Default()
                            .address$(info.deviceAddress())
                            .range(info.range().bytes())
                            .format(info.format().qualifier())
            );
        }
    }

    record StorageTexelBuffer(@NotNull VulkanicDescriptorAddressInfo info) implements VulkanicDescriptorData {
        @Override
        public void get(VkDescriptorDataEXT pData) {
            pData.pStorageTexelBuffer(
                    VkDescriptorAddressInfoEXT.calloc(MemoryStack.stackGet())
                            .sType$Default()
                            .address$(info.deviceAddress())
                            .range(info.range().bytes())
                            .format(info.format().qualifier())
            );
        }
    }

    record UniformBuffer(@NotNull VulkanicDescriptorAddressInfo info) implements VulkanicDescriptorData {
        @Override
        public void get(VkDescriptorDataEXT pData) {
            pData.pUniformBuffer(
                    VkDescriptorAddressInfoEXT.calloc(MemoryStack.stackGet())
                            .sType$Default()
                            .address$(info.deviceAddress())
                            .range(info.range().bytes())
                            .format(info.format().qualifier())
            );
        }
    }

    record StorageBuffer(@NotNull VulkanicDescriptorAddressInfo info) implements VulkanicDescriptorData {
        @Override
        public void get(VkDescriptorDataEXT pData) {
            pData.pStorageBuffer(
                    VkDescriptorAddressInfoEXT.calloc(MemoryStack.stackGet())
                            .sType$Default()
                            .address$(info.deviceAddress())
                            .range(info.range().bytes())
                            .format(info.format().qualifier())
            );
        }
    }

    // this distinction matters
    record AccelerationStructureKHR(long deviceAddress) implements VulkanicDescriptorData {
        @Override
        public void get(VkDescriptorDataEXT pData) {
            pData.accelerationStructure(deviceAddress);
        }
    }

    record AccelerationStructureNV(long deviceAddress) implements VulkanicDescriptorData {
        @Override
        public void get(VkDescriptorDataEXT pData) {
            pData.accelerationStructure(deviceAddress);
        }
    }
}
