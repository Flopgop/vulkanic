package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.*;

public enum VulkanicRenderingFlag implements Bitmaskable<Integer> {
    CONTENTS_SECONDARY_COMMAND_BUFFERS(VK13.VK_RENDERING_CONTENTS_SECONDARY_COMMAND_BUFFERS_BIT),
    SUSPENDING(VK13.VK_RENDERING_SUSPENDING_BIT),
    RESUMING(VK13.VK_RENDERING_RESUMING_BIT),

    ENABLE_LEGACY_DITHERING_EXT(EXTLegacyDithering.VK_RENDERING_ENABLE_LEGACY_DITHERING_BIT_EXT),

    CONTENTS_INLINE_KHR(KHRMaintenance7.VK_RENDERING_CONTENTS_INLINE_BIT_KHR),

    PER_LAYER_FRAGMENT_DENSITY_VALVE(VALVEFragmentDensityMapLayered.VK_RENDERING_PER_LAYER_FRAGMENT_DENSITY_BIT_VALVE),

    FRAGMENT_REGION_EXT(EXTCustomResolve.VK_RENDERING_FRAGMENT_REGION_BIT_EXT),
    CUSTOM_RESOLVE_EXT(EXTCustomResolve.VK_RENDERING_CUSTOM_RESOLVE_BIT_EXT),

    LOCAL_READ_CONCURRENT_ACCESS_CONTROL_KHR(KHRMaintenance10.VK_RENDERING_LOCAL_READ_CONCURRENT_ACCESS_CONTROL_BIT_KHR),
    ;

    private final int flag;
    VulkanicRenderingFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
