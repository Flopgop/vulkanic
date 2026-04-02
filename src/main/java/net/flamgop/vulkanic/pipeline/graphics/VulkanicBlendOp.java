package net.flamgop.vulkanic.pipeline.graphics;

import org.lwjgl.vulkan.EXTBlendOperationAdvanced;
import org.lwjgl.vulkan.VK10;

public enum VulkanicBlendOp {
    ADD(VK10.VK_BLEND_OP_ADD),
    SUBTRACT(VK10.VK_BLEND_OP_SUBTRACT),
    REVERSE_SUBTRACT(VK10.VK_BLEND_OP_REVERSE_SUBTRACT),
    MIN(VK10.VK_BLEND_OP_MIN),
    MAX(VK10.VK_BLEND_OP_MAX),
    ZERO_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_ZERO_EXT),
    SRC_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_SRC_EXT),
    DST_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_DST_EXT),
    SRC_OVER_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_SRC_OVER_EXT),
    DST_OVER_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_DST_OVER_EXT),
    SRC_IN_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_SRC_IN_EXT),
    DST_IN_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_DST_IN_EXT),
    SRC_OUT_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_SRC_OUT_EXT),
    DST_OUT_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_DST_OUT_EXT),
    SRC_ATOP_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_SRC_ATOP_EXT),
    DST_ATOP_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_DST_ATOP_EXT),
    XOR_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_XOR_EXT),
    MULTIPLY_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_MULTIPLY_EXT),
    SCREEN_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_SCREEN_EXT),
    OVERLAY_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_OVERLAY_EXT),
    DARKEN_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_DARKEN_EXT),
    LIGHTEN_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_LIGHTEN_EXT),
    COLORDODGE_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_COLORDODGE_EXT),
    COLORBURN_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_COLORBURN_EXT),
    HARDLIGHT_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_HARDLIGHT_EXT),
    SOFTLIGHT_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_SOFTLIGHT_EXT),
    DIFFERENCE_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_DIFFERENCE_EXT),
    EXCLUSION_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_EXCLUSION_EXT),
    INVERT_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_INVERT_EXT),
    INVERT_RGB_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_INVERT_RGB_EXT),
    LINEARDODGE_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_LINEARDODGE_EXT),
    LINEARBURN_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_LINEARBURN_EXT),
    VIVIDLIGHT_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_VIVIDLIGHT_EXT),
    LINEARLIGHT_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_LINEARLIGHT_EXT),
    PINLIGHT_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_PINLIGHT_EXT),
    HARDMIX_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_HARDMIX_EXT),
    HSL_HUE_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_HSL_HUE_EXT),
    HSL_SATURATION_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_HSL_SATURATION_EXT),
    HSL_COLOR_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_HSL_COLOR_EXT),
    HSL_LUMINOSITY_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_HSL_LUMINOSITY_EXT),
    PLUS_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_PLUS_EXT),
    PLUS_CLAMPED_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_PLUS_CLAMPED_EXT),
    PLUS_CLAMPED_ALPHA_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_PLUS_CLAMPED_ALPHA_EXT),
    PLUS_DARKER_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_PLUS_DARKER_EXT),
    MINUS_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_MINUS_EXT),
    MINUS_CLAMPED_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_MINUS_CLAMPED_EXT),
    CONTRAST_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_CONTRAST_EXT),
    INVERT_OVG_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_INVERT_OVG_EXT),
    RED_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_RED_EXT),
    GREEN_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_GREEN_EXT),
    BLUE_EXT(EXTBlendOperationAdvanced.VK_BLEND_OP_BLUE_EXT),
    ;

    private final int qualifier;
    VulkanicBlendOp(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
