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

public final class SpvReflectBlockVariable {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("spirv_id"),
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("name"),
            ValueLayout.JAVA_INT.withName("offset"),
            ValueLayout.JAVA_INT.withName("absolute_offset"),
            ValueLayout.JAVA_INT.withName("size"),
            ValueLayout.JAVA_INT.withName("padded_size"),
            ValueLayout.JAVA_INT.withName("decoration_flags"),
            SpvReflectNumericTraits.LAYOUT.withName("numeric"),
            SpvReflectArrayTraits.LAYOUT.withName("array"),
            ValueLayout.JAVA_INT.withName("flags"),
            ValueLayout.JAVA_INT.withName("member_count"),
            ValueLayout.ADDRESS.withName("members"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(net.flamgop.vulkanic.reflect.bindings.SpvReflectTypeDescription.LAYOUT)).withName("type_description"),
            SpvReflectBlockVariable_WordOffset.LAYOUT.withName("word_offset")
    );

    private static final VarHandle VH_SPIRV_ID = LAYOUT.varHandle(PathElement.groupElement("spirv_id"));
    private static final VarHandle VH_NAME = LAYOUT.varHandle(PathElement.groupElement("name"));
    private static final VarHandle VH_OFFSET = LAYOUT.varHandle(PathElement.groupElement("offset"));
    private static final VarHandle VH_ABSOLUTE_OFFSET = LAYOUT.varHandle(PathElement.groupElement("absolute_offset"));
    private static final VarHandle VH_SIZE = LAYOUT.varHandle(PathElement.groupElement("size"));
    private static final VarHandle VH_PADDED_SIZE = LAYOUT.varHandle(PathElement.groupElement("padded_size"));
    private static final VarHandle VH_DECORATION_FLAGS = LAYOUT.varHandle(PathElement.groupElement("decoration_flags"));
    private static final VarHandle VH_FLAGS = LAYOUT.varHandle(PathElement.groupElement("flags"));
    private static final VarHandle VH_MEMBER_COUNT = LAYOUT.varHandle(PathElement.groupElement("member_count"));
    private static final VarHandle VH_MEMBERS = LAYOUT.varHandle(PathElement.groupElement("members"));
    private static final VarHandle VH_TYPE_DESCRIPTION = LAYOUT.varHandle(PathElement.groupElement("type_description"));

    private static final long NUMERIC_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("numeric"));
    private static final long ARRAY_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("array"));
    private static final long WORD_OFFSET_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("word_offset"));

    private final MemorySegment segment;

    
    public SpvReflectBlockVariable(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectBlockVariable(MemorySegment segment) {
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

    public int offset() {
        return (int) VH_OFFSET.get(segment, 0L);
    }

    public int absoluteOffset() {
        return (int) VH_ABSOLUTE_OFFSET.get(segment, 0L);
    }

    public int size() {
        return (int) VH_SIZE.get(segment, 0L);
    }

    public int paddedSize() {
        return (int) VH_PADDED_SIZE.get(segment, 0L);
    }

    public EnumIntBitset<SpvReflectDecorationFlagBits> decorationFlags() {
        return new EnumIntBitset<>((int) VH_DECORATION_FLAGS.get(segment, 0L));
    }

    public SpvReflectNumericTraits numeric() {
        long size = SpvReflectNumericTraits.LAYOUT.byteSize();
        return new SpvReflectNumericTraits(segment.asSlice(NUMERIC_OFFSET, size));
    }

    public SpvReflectArrayTraits array() {
        long size = SpvReflectArrayTraits.LAYOUT.byteSize();
        return new SpvReflectArrayTraits(segment.asSlice(ARRAY_OFFSET, size));
    }

    public EnumIntBitset<SpvReflectVariableFlagBits> flags() {
        return new EnumIntBitset<>((int) VH_FLAGS.get(segment, 0L));
    }

    public int memberCount() {
        return (int) VH_MEMBER_COUNT.get(segment, 0L);
    }

    public SpvReflectBlockVariable member(int index) {
        MemorySegment base = (MemorySegment) VH_MEMBERS.get(segment, 0L);
        base = base.reinterpret(Long.MAX_VALUE);
        long elemSize = SpvReflectBlockVariable.LAYOUT.byteSize();
        return new SpvReflectBlockVariable(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectBlockVariable> members() {
        int count = memberCount();
        List<SpvReflectBlockVariable> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(member(i));
        }
        return list;
    }

    
    public net.flamgop.vulkanic.reflect.bindings.SpvReflectTypeDescription typeDescription() {
        MemorySegment ptr = (MemorySegment) VH_TYPE_DESCRIPTION.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return new SpvReflectTypeDescription(ptr);
    }

    public SpvReflectBlockVariable_WordOffset wordOffset() {
        long size = SpvReflectBlockVariable_WordOffset.LAYOUT.byteSize();
        return new SpvReflectBlockVariable_WordOffset(segment.asSlice(WORD_OFFSET_OFFSET, size));
    }
}
