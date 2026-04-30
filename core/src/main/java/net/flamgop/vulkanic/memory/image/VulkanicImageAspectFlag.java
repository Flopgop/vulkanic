package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.EXTImageDrmFormatModifier;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK11;
import org.lwjgl.vulkan.VK13;

public enum VulkanicImageAspectFlag implements Bitmaskable<Integer> {
    COLOR(VK10.VK_IMAGE_ASPECT_COLOR_BIT),
    DEPTH(VK10.VK_IMAGE_ASPECT_DEPTH_BIT),
    STENCIL(VK10.VK_IMAGE_ASPECT_STENCIL_BIT),
    METADATA(VK10.VK_IMAGE_ASPECT_METADATA_BIT),

    PLANE_0(VK11.VK_IMAGE_ASPECT_PLANE_0_BIT),
    PLANE_1(VK11.VK_IMAGE_ASPECT_PLANE_1_BIT),
    PLANE_2(VK11.VK_IMAGE_ASPECT_PLANE_2_BIT),

    NONE(VK13.VK_IMAGE_ASPECT_NONE),

    MEMORY_PLANE_0_EXT(EXTImageDrmFormatModifier.VK_IMAGE_ASPECT_MEMORY_PLANE_0_BIT_EXT),
    MEMORY_PLANE_1_EXT(EXTImageDrmFormatModifier.VK_IMAGE_ASPECT_MEMORY_PLANE_1_BIT_EXT),
    MEMORY_PLANE_2_EXT(EXTImageDrmFormatModifier.VK_IMAGE_ASPECT_MEMORY_PLANE_2_BIT_EXT),
    MEMORY_PLANE_3_EXT(EXTImageDrmFormatModifier.VK_IMAGE_ASPECT_MEMORY_PLANE_3_BIT_EXT),
    ;

    private final int flag;
    VulkanicImageAspectFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
