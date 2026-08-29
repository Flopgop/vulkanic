package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectTypeDescription_Traits {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            SpvReflectNumericTraits.LAYOUT.withName("numeric"),
            net.flamgop.vulkanic.reflect.bindings.SpvReflectImageTraits.LAYOUT.withName("image"),
            SpvReflectArrayTraits.LAYOUT.withName("array")
    );

    private static final long NUMERIC_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("numeric"));
    private static final long IMAGE_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("image"));
    private static final long ARRAY_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("array"));

    private final MemorySegment segment;

    
    public SpvReflectTypeDescription_Traits(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectTypeDescription_Traits(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public SpvReflectNumericTraits numeric() {
        long size = SpvReflectNumericTraits.LAYOUT.byteSize();
        return new SpvReflectNumericTraits(segment.asSlice(NUMERIC_OFFSET, size));
    }

    public net.flamgop.vulkanic.reflect.bindings.SpvReflectImageTraits image() {
        long size = net.flamgop.vulkanic.reflect.bindings.SpvReflectImageTraits.LAYOUT.byteSize();
        return new SpvReflectImageTraits(segment.asSlice(IMAGE_OFFSET, size));
    }

    public SpvReflectArrayTraits array() {
        long size = SpvReflectArrayTraits.LAYOUT.byteSize();
        return new SpvReflectArrayTraits(segment.asSlice(ARRAY_OFFSET, size));
    }
}
