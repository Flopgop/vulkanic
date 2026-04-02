package net.flamgop.vulkanic.swapchain;

import org.lwjgl.vulkan.EXTPresentModeFifoLatestReady;
import org.lwjgl.vulkan.KHRPresentModeFifoLatestReady;
import org.lwjgl.vulkan.KHRSharedPresentableImage;
import org.lwjgl.vulkan.KHRSurface;

public enum VulkanicPresentMode {
    IMMEDIATE_KHR(KHRSurface.VK_PRESENT_MODE_IMMEDIATE_KHR),
    MAILBOX_KHR(KHRSurface.VK_PRESENT_MODE_MAILBOX_KHR),
    FIFO_KHR(KHRSurface.VK_PRESENT_MODE_FIFO_KHR),
    FIFO_RELAXED_KHR(KHRSurface.VK_PRESENT_MODE_FIFO_RELAXED_KHR),
    SHARED_DEMAND_REFRESH_KHR(KHRSharedPresentableImage.VK_PRESENT_MODE_SHARED_DEMAND_REFRESH_KHR),
    SHARED_CONTINUOUS_REFRESH_KHR(KHRSharedPresentableImage.VK_PRESENT_MODE_SHARED_CONTINUOUS_REFRESH_KHR),
    FIFO_LATEST_READY_KHR(KHRPresentModeFifoLatestReady.VK_PRESENT_MODE_FIFO_LATEST_READY_KHR),
    FIFO_LATEST_READY_EXT(EXTPresentModeFifoLatestReady.VK_PRESENT_MODE_FIFO_LATEST_READY_EXT)

    ;

    private final int qualifier;
    VulkanicPresentMode(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
