package net.flamgop.vulkanic.reflect.bindings;

public enum SpvStorageClass {
    SpvStorageClassUniformConstant(0),
    SpvStorageClassInput(1),
    SpvStorageClassUniform(2),
    SpvStorageClassOutput(3),
    SpvStorageClassWorkgroup(4),
    SpvStorageClassCrossWorkgroup(5),
    SpvStorageClassPrivate(6),
    SpvStorageClassFunction(7),
    SpvStorageClassGeneric(8),
    SpvStorageClassPushConstant(9),
    SpvStorageClassAtomicCounter(10),
    SpvStorageClassImage(11),
    SpvStorageClassStorageBuffer(12),
    SpvStorageClassTileImageEXT(4172),
    SpvStorageClassTileAttachmentQCOM(4491),
    SpvStorageClassNodePayloadAMDX(5068),
    SpvStorageClassCallableDataKHR(5328),
    SpvStorageClassCallableDataNV(5328),
    SpvStorageClassIncomingCallableDataKHR(5329),
    SpvStorageClassIncomingCallableDataNV(5329),
    SpvStorageClassRayPayloadKHR(5338),
    SpvStorageClassRayPayloadNV(5338),
    SpvStorageClassHitAttributeKHR(5339),
    SpvStorageClassHitAttributeNV(5339),
    SpvStorageClassIncomingRayPayloadKHR(5342),
    SpvStorageClassIncomingRayPayloadNV(5342),
    SpvStorageClassShaderRecordBufferKHR(5343),
    SpvStorageClassShaderRecordBufferNV(5343),
    SpvStorageClassPhysicalStorageBuffer(5349),
    SpvStorageClassPhysicalStorageBufferEXT(5349),
    SpvStorageClassHitObjectAttributeNV(5385),
    SpvStorageClassTaskPayloadWorkgroupEXT(5402),
    SpvStorageClassHitObjectAttributeEXT(5411),
    SpvStorageClassCodeSectionINTEL(5605),
    SpvStorageClassDeviceOnlyALTERA(5936),
    SpvStorageClassDeviceOnlyINTEL(5936),
    SpvStorageClassHostOnlyALTERA(5937),
    SpvStorageClassHostOnlyINTEL(5937),
    SpvStorageClassMax(2147483647);

    private final int value;

    SpvStorageClass(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvStorageClass fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvStorageClass value: " + value);
    }
}
