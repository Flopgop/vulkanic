package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectBindingArrayTraits {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("dims_count"),
            MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_INT).withName("dims")
    );

    private static final VarHandle VH_DIMS_COUNT = LAYOUT.varHandle(PathElement.groupElement("dims_count"));

    private static final long DIMS_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("dims"));

    private final MemorySegment segment;

    
    public SpvReflectBindingArrayTraits(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectBindingArrayTraits(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int dimsCount() {
        return (int) VH_DIMS_COUNT.get(segment, 0L);
    }

    public int dim(int index) {
        long elemSize = ValueLayout.JAVA_INT.byteSize();
        MemorySegment elem = segment.asSlice(DIMS_OFFSET + (long) index * elemSize, elemSize);
        return (int) elem.get(ValueLayout.JAVA_INT, 0L);
    }
}
