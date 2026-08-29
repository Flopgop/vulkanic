package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.EnumIntBitset;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectDescriptorBinding {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("spirv_id"),
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("name"),
            ValueLayout.JAVA_INT.withName("binding"),
            ValueLayout.JAVA_INT.withName("input_attachment_index"),
            ValueLayout.JAVA_INT.withName("set"),
            ValueLayout.JAVA_INT.withName("descriptor_type"),
            ValueLayout.JAVA_INT.withName("resource_type"),
            SpvReflectImageTraits.LAYOUT.withName("image"),
            SpvReflectBlockVariable.LAYOUT.withName("block"),
            SpvReflectBindingArrayTraits.LAYOUT.withName("array"),
            ValueLayout.JAVA_INT.withName("count"),
            ValueLayout.JAVA_INT.withName("accessed"),
            ValueLayout.JAVA_INT.withName("uav_counter_id"),
            ValueLayout.ADDRESS.withName("uav_counter_binding"),
            ValueLayout.JAVA_INT.withName("byte_address_buffer_offset_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(ValueLayout.JAVA_INT)).withName("byte_address_buffer_offsets"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectTypeDescription.LAYOUT)).withName("type_description"),
            SpvReflectDescriptorBinding_WordOffset.LAYOUT.withName("word_offset"),
            ValueLayout.JAVA_INT.withName("decoration_flags"),
            ValueLayout.JAVA_INT.withName("user_type")
    );

    private static final VarHandle VH_SPIRV_ID = LAYOUT.varHandle(PathElement.groupElement("spirv_id"));
    private static final VarHandle VH_NAME = LAYOUT.varHandle(PathElement.groupElement("name"));
    private static final VarHandle VH_BINDING = LAYOUT.varHandle(PathElement.groupElement("binding"));
    private static final VarHandle VH_INPUT_ATTACHMENT_INDEX = LAYOUT.varHandle(PathElement.groupElement("input_attachment_index"));
    private static final VarHandle VH_SET = LAYOUT.varHandle(PathElement.groupElement("set"));
    private static final VarHandle VH_DESCRIPTOR_TYPE = LAYOUT.varHandle(PathElement.groupElement("descriptor_type"));
    private static final VarHandle VH_RESOURCE_TYPE = LAYOUT.varHandle(PathElement.groupElement("resource_type"));
    private static final VarHandle VH_COUNT = LAYOUT.varHandle(PathElement.groupElement("count"));
    private static final VarHandle VH_ACCESSED = LAYOUT.varHandle(PathElement.groupElement("accessed"));
    private static final VarHandle VH_UAV_COUNTER_ID = LAYOUT.varHandle(PathElement.groupElement("uav_counter_id"));
    private static final VarHandle VH_UAV_COUNTER_BINDING = LAYOUT.varHandle(PathElement.groupElement("uav_counter_binding"));
    private static final VarHandle VH_BYTE_ADDRESS_BUFFER_OFFSET_COUNT = LAYOUT.varHandle(PathElement.groupElement("byte_address_buffer_offset_count"));
    private static final VarHandle VH_BYTE_ADDRESS_BUFFER_OFFSETS = LAYOUT.varHandle(PathElement.groupElement("byte_address_buffer_offsets"));
    private static final VarHandle VH_TYPE_DESCRIPTION = LAYOUT.varHandle(PathElement.groupElement("type_description"));
    private static final VarHandle VH_DECORATION_FLAGS = LAYOUT.varHandle(PathElement.groupElement("decoration_flags"));
    private static final VarHandle VH_USER_TYPE = LAYOUT.varHandle(PathElement.groupElement("user_type"));

    private static final long IMAGE_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("image"));
    private static final long BLOCK_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("block"));
    private static final long ARRAY_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("array"));
    private static final long WORD_OFFSET_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("word_offset"));

    private final MemorySegment segment;

    
    public SpvReflectDescriptorBinding(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectDescriptorBinding(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int spirvId() {
        return (int) VH_SPIRV_ID.get(segment, 0L);
    }

    
    public String name() {
        MemorySegment ptr = (MemorySegment) VH_NAME.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return ptr.getString(0);
    }

    public int binding() {
        return (int) VH_BINDING.get(segment, 0L);
    }

    public int inputAttachmentIndex() {
        return (int) VH_INPUT_ATTACHMENT_INDEX.get(segment, 0L);
    }

    public int set() {
        return (int) VH_SET.get(segment, 0L);
    }

    public SpvReflectDescriptorType descriptorType() {
        return SpvReflectDescriptorType.fromValue((int) VH_DESCRIPTOR_TYPE.get(segment, 0L));
    }

    public EnumIntBitset<SpvReflectResourceType> resourceType() {
        return new EnumIntBitset<>((int) VH_RESOURCE_TYPE.get(segment, 0L));
    }

    public SpvReflectImageTraits image() {
        long size = SpvReflectImageTraits.LAYOUT.byteSize();
        return new SpvReflectImageTraits(segment.asSlice(IMAGE_OFFSET, size));
    }

    public SpvReflectBlockVariable block() {
        long size = SpvReflectBlockVariable.LAYOUT.byteSize();
        return new SpvReflectBlockVariable(segment.asSlice(BLOCK_OFFSET, size));
    }

    public SpvReflectBindingArrayTraits array() {
        long size = SpvReflectBindingArrayTraits.LAYOUT.byteSize();
        return new SpvReflectBindingArrayTraits(segment.asSlice(ARRAY_OFFSET, size));
    }

    public int count() {
        return (int) VH_COUNT.get(segment, 0L);
    }

    public int accessed() {
        return (int) VH_ACCESSED.get(segment, 0L);
    }

    public int uavCounterId() {
        return (int) VH_UAV_COUNTER_ID.get(segment, 0L);
    }

    
    public SpvReflectDescriptorBinding uavCounterBinding() {
        MemorySegment ptr = (MemorySegment) VH_UAV_COUNTER_BINDING.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return new SpvReflectDescriptorBinding(ptr);
    }

    public int byteAddressBufferOffsetCount() {
        return (int) VH_BYTE_ADDRESS_BUFFER_OFFSET_COUNT.get(segment, 0L);
    }

    public MemorySegment byteAddressBufferOffsets() {
        return (MemorySegment) VH_BYTE_ADDRESS_BUFFER_OFFSETS.get(segment, 0L);
    }

    
    public SpvReflectTypeDescription typeDescription() {
        MemorySegment ptr = (MemorySegment) VH_TYPE_DESCRIPTION.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return new SpvReflectTypeDescription(ptr);
    }

    public SpvReflectDescriptorBinding_WordOffset wordOffset() {
        long size = SpvReflectDescriptorBinding_WordOffset.LAYOUT.byteSize();
        return new SpvReflectDescriptorBinding_WordOffset(segment.asSlice(WORD_OFFSET_OFFSET, size));
    }

    public EnumIntBitset<SpvReflectDecorationFlagBits> decorationFlags() {
        return new EnumIntBitset<>((int) VH_DECORATION_FLAGS.get(segment, 0L));
    }

    public SpvReflectUserType userType() {
        return SpvReflectUserType.fromValue((int) VH_USER_TYPE.get(segment, 0L));
    }
}
