package net.flamgop.vulkanic.reflect.bindings;

public enum SpvFunctionParameterAttribute {
    SpvFunctionParameterAttributeZext(0),
    SpvFunctionParameterAttributeSext(1),
    SpvFunctionParameterAttributeByVal(2),
    SpvFunctionParameterAttributeSret(3),
    SpvFunctionParameterAttributeNoAlias(4),
    SpvFunctionParameterAttributeNoCapture(5),
    SpvFunctionParameterAttributeNoWrite(6),
    SpvFunctionParameterAttributeNoReadWrite(7),
    SpvFunctionParameterAttributeRuntimeAlignedALTERA(5940),
    SpvFunctionParameterAttributeRuntimeAlignedINTEL(5940),
    SpvFunctionParameterAttributeMax(2147483647);

    private final int value;

    SpvFunctionParameterAttribute(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvFunctionParameterAttribute fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvFunctionParameterAttribute value: " + value);
    }
}
