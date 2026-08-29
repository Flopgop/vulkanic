package net.flamgop.vulkanic.reflect.bindings;

public enum SpvKernelEnqueueFlags {
    SpvKernelEnqueueFlagsNoWait(0),
    SpvKernelEnqueueFlagsWaitKernel(1),
    SpvKernelEnqueueFlagsWaitWorkGroup(2),
    SpvKernelEnqueueFlagsMax(2147483647);

    private final int value;

    SpvKernelEnqueueFlags(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvKernelEnqueueFlags fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvKernelEnqueueFlags value: " + value);
    }
}
