package net.flamgop.vulkanic.reflect.bindings;

public enum SpvKernelProfilingInfoShift {
    SpvKernelProfilingInfoCmdExecTimeShift(0),
    SpvKernelProfilingInfoMax(2147483647);

    private final int value;

    SpvKernelProfilingInfoShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvKernelProfilingInfoShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvKernelProfilingInfoShift value: " + value);
    }
}
