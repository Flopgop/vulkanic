package net.flamgop.vulkanic.pipeline.graphics;

import org.lwjgl.vulkan.NVFillRectangle;
import org.lwjgl.vulkan.VK11;

public enum VulkanicPolygonMode {
    FILL(VK11.VK_POLYGON_MODE_FILL),
    LINE(VK11.VK_POLYGON_MODE_LINE),
    POINT(VK11.VK_POLYGON_MODE_POINT),
    RECTANGLE_NV(NVFillRectangle.VK_POLYGON_MODE_FILL_RECTANGLE_NV)
    ;

    private final int qualifier;
    VulkanicPolygonMode(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
