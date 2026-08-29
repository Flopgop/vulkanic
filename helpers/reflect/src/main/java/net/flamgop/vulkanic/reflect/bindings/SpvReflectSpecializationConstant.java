package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;
import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectSpecializationConstant {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("spirv_id"),
            ValueLayout.JAVA_INT.withName("constant_id"),
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("name"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectTypeDescription.LAYOUT)).withName("type_description"),
            ValueLayout.JAVA_INT.withName("default_value_size"),
            ValueLayout.ADDRESS.withName("default_value")
    );

    private static final VarHandle VH_SPIRV_ID = LAYOUT.varHandle(PathElement.groupElement("spirv_id"));
    private static final VarHandle VH_CONSTANT_ID = LAYOUT.varHandle(PathElement.groupElement("constant_id"));
    private static final VarHandle VH_NAME = LAYOUT.varHandle(PathElement.groupElement("name"));
    private static final VarHandle VH_TYPE_DESCRIPTION = LAYOUT.varHandle(PathElement.groupElement("type_description"));
    private static final VarHandle VH_DEFAULT_VALUE_SIZE = LAYOUT.varHandle(PathElement.groupElement("default_value_size"));
    private static final VarHandle VH_DEFAULT_VALUE = LAYOUT.varHandle(PathElement.groupElement("default_value"));

    private final MemorySegment segment;

    
    public SpvReflectSpecializationConstant(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectSpecializationConstant(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int spirvId() {
        return (int) VH_SPIRV_ID.get(segment, 0L);
    }

    public int constantId() {
        return (int) VH_CONSTANT_ID.get(segment, 0L);
    }

    
    public String name() {
        MemorySegment ptr = (MemorySegment) VH_NAME.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return ptr.getString(0);
    }

    
    public SpvReflectTypeDescription typeDescription() {
        MemorySegment ptr = (MemorySegment) VH_TYPE_DESCRIPTION.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return new SpvReflectTypeDescription(ptr);
    }

    public int defaultValueSize() {
        return (int) VH_DEFAULT_VALUE_SIZE.get(segment, 0L);
    }

    public MemorySegment defaultValue() {
        return (MemorySegment) VH_DEFAULT_VALUE.get(segment, 0L);
    }
}
