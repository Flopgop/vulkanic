package net.flamgop.vulkanic.reflect.bindings;

public enum SpvGatherModes {
    SpvGatherModesGather4x1QCOM(0),
    SpvGatherModesGatherDQCOM(1),
    SpvGatherModesGatherH2QCOM(2),
    SpvGatherModesGatherV2QCOM(3),
    SpvGatherModesMax(2147483647);

    private final int value;

    SpvGatherModes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvGatherModes fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvGatherModes value: " + value);
    }
}
