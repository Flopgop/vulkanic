package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectEntryPointSamplerHeapAccess {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("heap_name"),
            ValueLayout.JAVA_INT.withName("runtime_array_type_id"),
            ValueLayout.JAVA_INT.withName("stride"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectTypeDescription.LAYOUT)).withName("type_description")
    );

    private static final VarHandle VH_HEAP_NAME = LAYOUT.varHandle(PathElement.groupElement("heap_name"));
    private static final VarHandle VH_RUNTIME_ARRAY_TYPE_ID = LAYOUT.varHandle(PathElement.groupElement("runtime_array_type_id"));
    private static final VarHandle VH_STRIDE = LAYOUT.varHandle(PathElement.groupElement("stride"));
    private static final VarHandle VH_TYPE_DESCRIPTION = LAYOUT.varHandle(PathElement.groupElement("type_description"));

    private final MemorySegment segment;

    
    public SpvReflectEntryPointSamplerHeapAccess(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectEntryPointSamplerHeapAccess(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    
    public String heapName() {
        MemorySegment ptr = (MemorySegment) VH_HEAP_NAME.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return ptr.getString(0);
    }

    public int runtimeArrayTypeId() {
        return (int) VH_RUNTIME_ARRAY_TYPE_ID.get(segment, 0L);
    }

    public int stride() {
        return (int) VH_STRIDE.get(segment, 0L);
    }

    
    public SpvReflectTypeDescription typeDescription() {
        MemorySegment ptr = (MemorySegment) VH_TYPE_DESCRIPTION.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return new SpvReflectTypeDescription(ptr);
    }
}
