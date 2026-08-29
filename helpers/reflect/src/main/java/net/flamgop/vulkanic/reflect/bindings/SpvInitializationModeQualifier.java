package net.flamgop.vulkanic.reflect.bindings;

public enum SpvInitializationModeQualifier {
    SpvInitializationModeQualifierInitOnDeviceReprogramALTERA(0),
    SpvInitializationModeQualifierInitOnDeviceReprogramINTEL(0),
    SpvInitializationModeQualifierInitOnDeviceResetALTERA(1),
    SpvInitializationModeQualifierInitOnDeviceResetINTEL(1),
    SpvInitializationModeQualifierMax(2147483647);

    private final int value;

    SpvInitializationModeQualifier(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvInitializationModeQualifier fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvInitializationModeQualifier value: " + value);
    }
}
