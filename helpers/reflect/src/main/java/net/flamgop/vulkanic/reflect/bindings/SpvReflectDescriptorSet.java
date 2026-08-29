package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectDescriptorSet {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("set"),
            ValueLayout.JAVA_INT.withName("binding_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(ValueLayout.ADDRESS)).withName("bindings")
    );

    private static final VarHandle VH_SET = LAYOUT.varHandle(PathElement.groupElement("set"));
    private static final VarHandle VH_BINDING_COUNT = LAYOUT.varHandle(PathElement.groupElement("binding_count"));
    private static final VarHandle VH_BINDINGS = LAYOUT.varHandle(PathElement.groupElement("bindings"));

    private final MemorySegment segment;

    
    public SpvReflectDescriptorSet(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectDescriptorSet(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int set() {
        return (int) VH_SET.get(segment, 0L);
    }

    public int bindingCount() {
        return (int) VH_BINDING_COUNT.get(segment, 0L);
    }

    public net.flamgop.vulkanic.reflect.bindings.SpvReflectDescriptorBinding binding(int index) {
        MemorySegment base = (MemorySegment) VH_BINDINGS.get(segment, 0L);
        MemorySegment elemPtr = base.getAtIndex(ValueLayout.ADDRESS, index);
        return new net.flamgop.vulkanic.reflect.bindings.SpvReflectDescriptorBinding(elemPtr);
    }

    public List<net.flamgop.vulkanic.reflect.bindings.SpvReflectDescriptorBinding> bindings() {
        int count = bindingCount();
        List<SpvReflectDescriptorBinding> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(binding(i));
        }
        return list;
    }
}
