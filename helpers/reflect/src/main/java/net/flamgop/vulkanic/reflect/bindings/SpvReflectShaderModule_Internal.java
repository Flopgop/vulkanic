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

public final class SpvReflectShaderModule_Internal {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("module_flags"),
            ValueLayout.JAVA_LONG.withName("spirv_size"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(ValueLayout.JAVA_INT)).withName("spirv_code"),
            ValueLayout.JAVA_INT.withName("spirv_word_count"),
            ValueLayout.JAVA_LONG.withName("type_description_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectTypeDescription.LAYOUT)).withName("type_descriptions")
    );

    private static final VarHandle VH_MODULE_FLAGS = LAYOUT.varHandle(PathElement.groupElement("module_flags"));
    private static final VarHandle VH_SPIRV_SIZE = LAYOUT.varHandle(PathElement.groupElement("spirv_size"));
    private static final VarHandle VH_SPIRV_CODE = LAYOUT.varHandle(PathElement.groupElement("spirv_code"));
    private static final VarHandle VH_SPIRV_WORD_COUNT = LAYOUT.varHandle(PathElement.groupElement("spirv_word_count"));
    private static final VarHandle VH_TYPE_DESCRIPTION_COUNT = LAYOUT.varHandle(PathElement.groupElement("type_description_count"));
    private static final VarHandle VH_TYPE_DESCRIPTIONS = LAYOUT.varHandle(PathElement.groupElement("type_descriptions"));

    private final MemorySegment segment;

    
    public SpvReflectShaderModule_Internal(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectShaderModule_Internal(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public EnumIntBitset<SpvReflectModuleFlagBits> moduleFlags() {
        return new EnumIntBitset<>((int) VH_MODULE_FLAGS.get(segment, 0L));
    }

    public long spirvSize() {
        return (long) VH_SPIRV_SIZE.get(segment, 0L);
    }

    public MemorySegment spirvCode() {
        return (MemorySegment) VH_SPIRV_CODE.get(segment, 0L);
    }

    public int spirvWordCount() {
        return (int) VH_SPIRV_WORD_COUNT.get(segment, 0L);
    }

    public long typeDescriptionCount() {
        return (long) VH_TYPE_DESCRIPTION_COUNT.get(segment, 0L);
    }

    public SpvReflectTypeDescription typeDescription(int index) {
        MemorySegment base = (MemorySegment) VH_TYPE_DESCRIPTIONS.get(segment, 0L);
        long elemSize = SpvReflectTypeDescription.LAYOUT.byteSize();
        return new SpvReflectTypeDescription(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectTypeDescription> typeDescriptions() {
        long count = typeDescriptionCount();
        List<SpvReflectTypeDescription> list = new ArrayList<>(Math.toIntExact(count));
        for (int i = 0; i < count; i++) {
            list.add(typeDescription(i));
        }
        return list;
    }
}
