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

public final class SpvReflectInterfaceVariable {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("spirv_id"),
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("name"),
            ValueLayout.JAVA_INT.withName("location"),
            ValueLayout.JAVA_INT.withName("component"),
            ValueLayout.JAVA_INT.withName("storage_class"),
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("semantic"),
            ValueLayout.JAVA_INT.withName("decoration_flags"),
            ValueLayout.JAVA_INT.withName("built_in"),
            SpvReflectNumericTraits.LAYOUT.withName("numeric"),
            SpvReflectArrayTraits.LAYOUT.withName("array"),
            ValueLayout.JAVA_INT.withName("member_count"),
            ValueLayout.ADDRESS.withName("members"),
            ValueLayout.JAVA_INT.withName("format"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectTypeDescription.LAYOUT)).withName("type_description"),
            SpvReflectInterfaceVariable_WordOffset.LAYOUT.withName("word_offset")
    );

    private static final VarHandle VH_SPIRV_ID = LAYOUT.varHandle(PathElement.groupElement("spirv_id"));
    private static final VarHandle VH_NAME = LAYOUT.varHandle(PathElement.groupElement("name"));
    private static final VarHandle VH_LOCATION = LAYOUT.varHandle(PathElement.groupElement("location"));
    private static final VarHandle VH_COMPONENT = LAYOUT.varHandle(PathElement.groupElement("component"));
    private static final VarHandle VH_STORAGE_CLASS = LAYOUT.varHandle(PathElement.groupElement("storage_class"));
    private static final VarHandle VH_SEMANTIC = LAYOUT.varHandle(PathElement.groupElement("semantic"));
    private static final VarHandle VH_DECORATION_FLAGS = LAYOUT.varHandle(PathElement.groupElement("decoration_flags"));
    private static final VarHandle VH_BUILT_IN = LAYOUT.varHandle(PathElement.groupElement("built_in"));
    private static final VarHandle VH_MEMBER_COUNT = LAYOUT.varHandle(PathElement.groupElement("member_count"));
    private static final VarHandle VH_MEMBERS = LAYOUT.varHandle(PathElement.groupElement("members"));
    private static final VarHandle VH_FORMAT = LAYOUT.varHandle(PathElement.groupElement("format"));
    private static final VarHandle VH_TYPE_DESCRIPTION = LAYOUT.varHandle(PathElement.groupElement("type_description"));

    private static final long NUMERIC_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("numeric"));
    private static final long ARRAY_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("array"));
    private static final long WORD_OFFSET_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("word_offset"));

    private final MemorySegment segment;

    
    public SpvReflectInterfaceVariable(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectInterfaceVariable(MemorySegment segment) {
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

    public int location() {
        return (int) VH_LOCATION.get(segment, 0L);
    }

    public int component() {
        return (int) VH_COMPONENT.get(segment, 0L);
    }

    public SpvStorageClass storageClass() {
        return SpvStorageClass.fromValue((int) VH_STORAGE_CLASS.get(segment, 0L));
    }

    
    public String semantic() {
        MemorySegment ptr = (MemorySegment) VH_SEMANTIC.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return ptr.getString(0);
    }

    public EnumIntBitset<SpvReflectDecorationFlagBits> decorationFlags() {
        return new EnumIntBitset<>((int) VH_DECORATION_FLAGS.get(segment, 0L));
    }

    public int builtIn() {
        return (int) VH_BUILT_IN.get(segment, 0L);
    }

    public SpvReflectNumericTraits numeric() {
        long size = SpvReflectNumericTraits.LAYOUT.byteSize();
        return new SpvReflectNumericTraits(segment.asSlice(NUMERIC_OFFSET, size));
    }

    public SpvReflectArrayTraits array() {
        long size = SpvReflectArrayTraits.LAYOUT.byteSize();
        return new SpvReflectArrayTraits(segment.asSlice(ARRAY_OFFSET, size));
    }

    public int memberCount() {
        return (int) VH_MEMBER_COUNT.get(segment, 0L);
    }

    public SpvReflectInterfaceVariable member(int index) {
        MemorySegment base = (MemorySegment) VH_MEMBERS.get(segment, 0L);
        base = base.reinterpret(Long.MAX_VALUE);
        long elemSize = SpvReflectInterfaceVariable.LAYOUT.byteSize();
        return new SpvReflectInterfaceVariable(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectInterfaceVariable> members() {
        int count = memberCount();
        List<SpvReflectInterfaceVariable> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(member(i));
        }
        return list;
    }

    public SpvReflectFormat format() {
        return SpvReflectFormat.fromValue((int) VH_FORMAT.get(segment, 0L));
    }

    
    public SpvReflectTypeDescription typeDescription() {
        MemorySegment ptr = (MemorySegment) VH_TYPE_DESCRIPTION.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return new SpvReflectTypeDescription(ptr);
    }

    public SpvReflectInterfaceVariable_WordOffset wordOffset() {
        long size = SpvReflectInterfaceVariable_WordOffset.LAYOUT.byteSize();
        return new SpvReflectInterfaceVariable_WordOffset(segment.asSlice(WORD_OFFSET_OFFSET, size));
    }
}
