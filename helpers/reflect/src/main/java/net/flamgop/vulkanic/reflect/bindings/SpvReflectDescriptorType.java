package net.flamgop.vulkanic.reflect.bindings;

public enum SpvReflectDescriptorType {
    SPV_REFLECT_DESCRIPTOR_TYPE_SAMPLER(0),
    SPV_REFLECT_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER(1),
    SPV_REFLECT_DESCRIPTOR_TYPE_SAMPLED_IMAGE(2),
    SPV_REFLECT_DESCRIPTOR_TYPE_STORAGE_IMAGE(3),
    SPV_REFLECT_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER(4),
    SPV_REFLECT_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER(5),
    SPV_REFLECT_DESCRIPTOR_TYPE_UNIFORM_BUFFER(6),
    SPV_REFLECT_DESCRIPTOR_TYPE_STORAGE_BUFFER(7),
    SPV_REFLECT_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC(8),
    SPV_REFLECT_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC(9),
    SPV_REFLECT_DESCRIPTOR_TYPE_INPUT_ATTACHMENT(10),
    SPV_REFLECT_DESCRIPTOR_TYPE_ACCELERATION_STRUCTURE_KHR(1000150000);

    private final int value;

    SpvReflectDescriptorType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvReflectDescriptorType fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvReflectDescriptorType value: " + value);
    }
}
