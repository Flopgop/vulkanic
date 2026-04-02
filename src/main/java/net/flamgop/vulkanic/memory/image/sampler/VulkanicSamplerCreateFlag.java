package net.flamgop.vulkanic.memory.image.sampler;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.EXTDescriptorBuffer;
import org.lwjgl.vulkan.EXTFragmentDensityMap;
import org.lwjgl.vulkan.EXTNonSeamlessCubeMap;
import org.lwjgl.vulkan.QCOMImageProcessing;

public enum VulkanicSamplerCreateFlag implements Bitmaskable<Integer> {
    SUBSAMPLED_EXT(EXTFragmentDensityMap.VK_SAMPLER_CREATE_SUBSAMPLED_BIT_EXT),
    SUBSAMPLED_COARSE_RECONSTRUCTION_EXT(EXTFragmentDensityMap.VK_SAMPLER_CREATE_SUBSAMPLED_COARSE_RECONSTRUCTION_BIT_EXT),
    DESCRIPTOR_BUFFER_CAPTURE_REPLAY_EXT(EXTDescriptorBuffer.VK_SAMPLER_CREATE_DESCRIPTOR_BUFFER_CAPTURE_REPLAY_BIT_EXT),
    NON_SEAMLESS_CUBE_MAP_EXT(EXTNonSeamlessCubeMap.VK_SAMPLER_CREATE_NON_SEAMLESS_CUBE_MAP_BIT_EXT),
    IMAGE_PROCESSING_QCOM(QCOMImageProcessing.VK_SAMPLER_CREATE_IMAGE_PROCESSING_BIT_QCOM)
    ;
    private final int flag;
    VulkanicSamplerCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
