package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.EnumIntBitset;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectTypeDescription {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("id"),
            ValueLayout.JAVA_INT.withName("op"),
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("type_name"),
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("struct_member_name"),
            ValueLayout.JAVA_INT.withName("storage_class"),
            ValueLayout.JAVA_INT.withName("type_flags"),
            ValueLayout.JAVA_INT.withName("decoration_flags"),
            SpvReflectTypeDescription_Traits.LAYOUT.withName("traits"),
            ValueLayout.ADDRESS.withName("struct_type_description"),
            ValueLayout.JAVA_INT.withName("copied"),
            ValueLayout.JAVA_INT.withName("member_count"),
            ValueLayout.ADDRESS.withName("members")
    );

    private static final VarHandle VH_ID = LAYOUT.varHandle(PathElement.groupElement("id"));
    private static final VarHandle VH_OP = LAYOUT.varHandle(PathElement.groupElement("op"));
    private static final VarHandle VH_TYPE_NAME = LAYOUT.varHandle(PathElement.groupElement("type_name"));
    private static final VarHandle VH_STRUCT_MEMBER_NAME = LAYOUT.varHandle(PathElement.groupElement("struct_member_name"));
    private static final VarHandle VH_STORAGE_CLASS = LAYOUT.varHandle(PathElement.groupElement("storage_class"));
    private static final VarHandle VH_TYPE_FLAGS = LAYOUT.varHandle(PathElement.groupElement("type_flags"));
    private static final VarHandle VH_DECORATION_FLAGS = LAYOUT.varHandle(PathElement.groupElement("decoration_flags"));
    private static final VarHandle VH_STRUCT_TYPE_DESCRIPTION = LAYOUT.varHandle(PathElement.groupElement("struct_type_description"));
    private static final VarHandle VH_COPIED = LAYOUT.varHandle(PathElement.groupElement("copied"));
    private static final VarHandle VH_MEMBER_COUNT = LAYOUT.varHandle(PathElement.groupElement("member_count"));
    private static final VarHandle VH_MEMBERS = LAYOUT.varHandle(PathElement.groupElement("members"));

    private static final long TRAITS_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("traits"));

    private final MemorySegment segment;

    
    public SpvReflectTypeDescription(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectTypeDescription(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int id() {
        return (int) VH_ID.get(segment, 0L);
    }

    public SpvOp op() {
        return SpvOp.fromValue((int) VH_OP.get(segment, 0L));
    }

    
    public String typeName() {
        MemorySegment ptr = (MemorySegment) VH_TYPE_NAME.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return ptr.getString(0);
    }

    
    public String structMemberName() {
        MemorySegment ptr = (MemorySegment) VH_STRUCT_MEMBER_NAME.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return ptr.getString(0);
    }

    public int storageClass() {
        return (int) VH_STORAGE_CLASS.get(segment, 0L);
    }

    public EnumIntBitset<SpvReflectTypeFlagBits> typeFlags() {
        return new EnumIntBitset<>((int) VH_TYPE_FLAGS.get(segment, 0L));
    }

    public EnumIntBitset<SpvReflectDecorationFlagBits> decorationFlags() {
        return new EnumIntBitset<>((int) VH_DECORATION_FLAGS.get(segment, 0L));
    }

    public SpvReflectTypeDescription_Traits traits() {
        long size = SpvReflectTypeDescription_Traits.LAYOUT.byteSize();
        return new SpvReflectTypeDescription_Traits(segment.asSlice(TRAITS_OFFSET, size));
    }

    
    public SpvReflectTypeDescription structTypeDescription() {
        MemorySegment ptr = (MemorySegment) VH_STRUCT_TYPE_DESCRIPTION.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return new SpvReflectTypeDescription(ptr);
    }

    public int copied() {
        return (int) VH_COPIED.get(segment, 0L);
    }

    public int memberCount() {
        return (int) VH_MEMBER_COUNT.get(segment, 0L);
    }

    public SpvReflectTypeDescription member(int index) {
        MemorySegment base = (MemorySegment) VH_MEMBERS.get(segment, 0L);
        base = base.reinterpret(Long.MAX_VALUE);
        long elemSize = SpvReflectTypeDescription.LAYOUT.byteSize();
        return new SpvReflectTypeDescription(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectTypeDescription> members() {
        int count = memberCount();
        List<SpvReflectTypeDescription> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(member(i));
        }
        return list;
    }
}
