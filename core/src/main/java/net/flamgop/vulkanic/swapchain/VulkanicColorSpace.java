package net.flamgop.vulkanic.swapchain;

import org.lwjgl.vulkan.AMDDisplayNativeHdr;
import org.lwjgl.vulkan.EXTSwapchainColorspace;
import org.lwjgl.vulkan.KHRSurface;

public enum VulkanicColorSpace {
    SRGB_NONLINEAR_KHR(KHRSurface.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR),
    DISPLAY_P3_NONLINEAR_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_DISPLAY_P3_NONLINEAR_EXT),
    EXTENDED_SRGB_LINEAR_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_EXTENDED_SRGB_LINEAR_EXT),
    DISPLAY_P3_LINEAR_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_DISPLAY_P3_LINEAR_EXT),
    DCI_P3_NONLINEAR_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_DCI_P3_NONLINEAR_EXT),
    BT709_LINEAR_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_BT709_LINEAR_EXT),
    BT709_NONLINEAR_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_BT709_NONLINEAR_EXT),
    BT2020_LINEAR_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_BT2020_LINEAR_EXT),
    HDR10_ST2084_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_HDR10_ST2084_EXT),
    DOLBYVISION_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_DOLBYVISION_EXT),
    HDR10_HLG_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_HDR10_HLG_EXT),
    ADOBERGB_LINEAR_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_ADOBERGB_LINEAR_EXT),
    ADOBERGB_NONLINEAR_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_ADOBERGB_NONLINEAR_EXT),
    PASS_THROUGH_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_PASS_THROUGH_EXT),
    EXTENDED_SRGB_NONLINEAR_EXT(EXTSwapchainColorspace.VK_COLOR_SPACE_EXTENDED_SRGB_NONLINEAR_EXT),
    DISPLAY_NATIVE_AMD(AMDDisplayNativeHdr.VK_COLOR_SPACE_DISPLAY_NATIVE_AMD),

    ;

    private final int qualifier;
    VulkanicColorSpace(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
