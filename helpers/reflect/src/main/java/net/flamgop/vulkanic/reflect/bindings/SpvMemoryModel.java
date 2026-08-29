package net.flamgop.vulkanic.reflect.bindings;

public enum SpvMemoryModel {
    SpvMemoryModelSimple(0),
    SpvMemoryModelGLSL450(1),
    SpvMemoryModelOpenCL(2),
    SpvMemoryModelVulkan(3),
    SpvMemoryModelVulkanKHR(3),
    SpvMemoryModelMax(2147483647);

    private final int value;

    SpvMemoryModel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvMemoryModel fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvMemoryModel value: " + value);
    }
}
