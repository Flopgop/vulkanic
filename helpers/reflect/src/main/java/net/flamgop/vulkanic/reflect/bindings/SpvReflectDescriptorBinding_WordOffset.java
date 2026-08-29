package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectDescriptorBinding_WordOffset {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("binding"),
            ValueLayout.JAVA_INT.withName("set")
    );

    private static final VarHandle VH_BINDING = LAYOUT.varHandle(PathElement.groupElement("binding"));
    private static final VarHandle VH_SET = LAYOUT.varHandle(PathElement.groupElement("set"));

    private final MemorySegment segment;

    
    public SpvReflectDescriptorBinding_WordOffset(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectDescriptorBinding_WordOffset(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int binding() {
        return (int) VH_BINDING.get(segment, 0L);
    }

    public int set() {
        return (int) VH_SET.get(segment, 0L);
    }
}
