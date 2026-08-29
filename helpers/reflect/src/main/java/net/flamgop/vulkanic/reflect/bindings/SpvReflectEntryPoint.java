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

public final class SpvReflectEntryPoint {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("name"),
            ValueLayout.JAVA_INT.withName("id"),
            ValueLayout.JAVA_INT.withName("spirv_execution_model"),
            ValueLayout.JAVA_INT.withName("shader_stage"),
            ValueLayout.JAVA_INT.withName("input_variable_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(ValueLayout.ADDRESS)).withName("input_variables"),
            ValueLayout.JAVA_INT.withName("output_variable_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(ValueLayout.ADDRESS)).withName("output_variables"),
            ValueLayout.JAVA_INT.withName("interface_variable_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectInterfaceVariable.LAYOUT)).withName("interface_variables"),
            ValueLayout.JAVA_INT.withName("descriptor_set_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(net.flamgop.vulkanic.reflect.bindings.SpvReflectDescriptorSet.LAYOUT)).withName("descriptor_sets"),
            ValueLayout.JAVA_INT.withName("used_uniform_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(ValueLayout.JAVA_INT)).withName("used_uniforms"),
            ValueLayout.JAVA_INT.withName("used_push_constant_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(ValueLayout.JAVA_INT)).withName("used_push_constants"),
            ValueLayout.JAVA_INT.withName("execution_mode_count"),
            ValueLayout.ADDRESS.withName("execution_modes"),
            SpvReflectEntryPoint_LocalSize.LAYOUT.withName("local_size"),
            ValueLayout.JAVA_INT.withName("invocations"),
            ValueLayout.JAVA_INT.withName("output_vertices"),
            ValueLayout.JAVA_INT.withName("resource_heap_access_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectEntryPointResourceHeapAccess.LAYOUT)).withName("resource_heap_accesses"),
            ValueLayout.JAVA_INT.withName("sampler_heap_access_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectEntryPointSamplerHeapAccess.LAYOUT)).withName("sampler_heap_accesses")
    );

    private static final VarHandle VH_NAME = LAYOUT.varHandle(PathElement.groupElement("name"));
    private static final VarHandle VH_ID = LAYOUT.varHandle(PathElement.groupElement("id"));
    private static final VarHandle VH_SPIRV_EXECUTION_MODEL = LAYOUT.varHandle(PathElement.groupElement("spirv_execution_model"));
    private static final VarHandle VH_SHADER_STAGE = LAYOUT.varHandle(PathElement.groupElement("shader_stage"));
    private static final VarHandle VH_INPUT_VARIABLE_COUNT = LAYOUT.varHandle(PathElement.groupElement("input_variable_count"));
    private static final VarHandle VH_INPUT_VARIABLES = LAYOUT.varHandle(PathElement.groupElement("input_variables"));
    private static final VarHandle VH_OUTPUT_VARIABLE_COUNT = LAYOUT.varHandle(PathElement.groupElement("output_variable_count"));
    private static final VarHandle VH_OUTPUT_VARIABLES = LAYOUT.varHandle(PathElement.groupElement("output_variables"));
    private static final VarHandle VH_INTERFACE_VARIABLE_COUNT = LAYOUT.varHandle(PathElement.groupElement("interface_variable_count"));
    private static final VarHandle VH_INTERFACE_VARIABLES = LAYOUT.varHandle(PathElement.groupElement("interface_variables"));
    private static final VarHandle VH_DESCRIPTOR_SET_COUNT = LAYOUT.varHandle(PathElement.groupElement("descriptor_set_count"));
    private static final VarHandle VH_DESCRIPTOR_SETS = LAYOUT.varHandle(PathElement.groupElement("descriptor_sets"));
    private static final VarHandle VH_USED_UNIFORM_COUNT = LAYOUT.varHandle(PathElement.groupElement("used_uniform_count"));
    private static final VarHandle VH_USED_UNIFORMS = LAYOUT.varHandle(PathElement.groupElement("used_uniforms"));
    private static final VarHandle VH_USED_PUSH_CONSTANT_COUNT = LAYOUT.varHandle(PathElement.groupElement("used_push_constant_count"));
    private static final VarHandle VH_USED_PUSH_CONSTANTS = LAYOUT.varHandle(PathElement.groupElement("used_push_constants"));
    private static final VarHandle VH_EXECUTION_MODE_COUNT = LAYOUT.varHandle(PathElement.groupElement("execution_mode_count"));
    private static final VarHandle VH_EXECUTION_MODES = LAYOUT.varHandle(PathElement.groupElement("execution_modes"));
    private static final VarHandle VH_INVOCATIONS = LAYOUT.varHandle(PathElement.groupElement("invocations"));
    private static final VarHandle VH_OUTPUT_VERTICES = LAYOUT.varHandle(PathElement.groupElement("output_vertices"));
    private static final VarHandle VH_RESOURCE_HEAP_ACCESS_COUNT = LAYOUT.varHandle(PathElement.groupElement("resource_heap_access_count"));
    private static final VarHandle VH_RESOURCE_HEAP_ACCESSES = LAYOUT.varHandle(PathElement.groupElement("resource_heap_accesses"));
    private static final VarHandle VH_SAMPLER_HEAP_ACCESS_COUNT = LAYOUT.varHandle(PathElement.groupElement("sampler_heap_access_count"));
    private static final VarHandle VH_SAMPLER_HEAP_ACCESSES = LAYOUT.varHandle(PathElement.groupElement("sampler_heap_accesses"));

    private static final long LOCAL_SIZE_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("local_size"));

    private final MemorySegment segment;

    
    public SpvReflectEntryPoint(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectEntryPoint(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    
    public String name() {
        MemorySegment ptr = (MemorySegment) VH_NAME.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return ptr.getString(0);
    }

    public int id() {
        return (int) VH_ID.get(segment, 0L);
    }

    public SpvExecutionModel spirvExecutionModel() {
        return SpvExecutionModel.fromValue((int) VH_SPIRV_EXECUTION_MODEL.get(segment, 0L));
    }

    public EnumIntBitset<SpvReflectShaderStageFlagBits> shaderStage() {
        return new EnumIntBitset<>((int) VH_SHADER_STAGE.get(segment, 0L));
    }

    public int inputVariableCount() {
        return (int) VH_INPUT_VARIABLE_COUNT.get(segment, 0L);
    }

    public SpvReflectInterfaceVariable inputVariable(int index) {
        MemorySegment base = (MemorySegment) VH_INPUT_VARIABLES.get(segment, 0L);
        MemorySegment elemPtr = base.getAtIndex(ValueLayout.ADDRESS, index);
        return new SpvReflectInterfaceVariable(elemPtr);
    }

    public List<SpvReflectInterfaceVariable> inputVariables() {
        int count = inputVariableCount();
        List<SpvReflectInterfaceVariable> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(inputVariable(i));
        }
        return list;
    }

    public int outputVariableCount() {
        return (int) VH_OUTPUT_VARIABLE_COUNT.get(segment, 0L);
    }

    public SpvReflectInterfaceVariable outputVariable(int index) {
        MemorySegment base = (MemorySegment) VH_OUTPUT_VARIABLES.get(segment, 0L);
        MemorySegment elemPtr = base.getAtIndex(ValueLayout.ADDRESS, index);
        return new SpvReflectInterfaceVariable(elemPtr);
    }

    public List<SpvReflectInterfaceVariable> outputVariables() {
        int count = outputVariableCount();
        List<SpvReflectInterfaceVariable> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(outputVariable(i));
        }
        return list;
    }

    public int interfaceVariableCount() {
        return (int) VH_INTERFACE_VARIABLE_COUNT.get(segment, 0L);
    }

    public SpvReflectInterfaceVariable interfaceVariable(int index) {
        MemorySegment base = (MemorySegment) VH_INTERFACE_VARIABLES.get(segment, 0L);
        long elemSize = SpvReflectInterfaceVariable.LAYOUT.byteSize();
        return new SpvReflectInterfaceVariable(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectInterfaceVariable> interfaceVariables() {
        int count = interfaceVariableCount();
        List<SpvReflectInterfaceVariable> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(interfaceVariable(i));
        }
        return list;
    }

    public int descriptorSetCount() {
        return (int) VH_DESCRIPTOR_SET_COUNT.get(segment, 0L);
    }

    public SpvReflectDescriptorSet descriptorSet(int index) {
        MemorySegment base = (MemorySegment) VH_DESCRIPTOR_SETS.get(segment, 0L);
        long elemSize = SpvReflectDescriptorSet.LAYOUT.byteSize();
        return new SpvReflectDescriptorSet(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectDescriptorSet> descriptorSets() {
        int count = descriptorSetCount();
        List<SpvReflectDescriptorSet> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(descriptorSet(i));
        }
        return list;
    }

    public int usedUniformCount() {
        return (int) VH_USED_UNIFORM_COUNT.get(segment, 0L);
    }

    public MemorySegment usedUniforms() {
        return (MemorySegment) VH_USED_UNIFORMS.get(segment, 0L);
    }

    public int usedPushConstantCount() {
        return (int) VH_USED_PUSH_CONSTANT_COUNT.get(segment, 0L);
    }

    public MemorySegment usedPushConstants() {
        return (MemorySegment) VH_USED_PUSH_CONSTANTS.get(segment, 0L);
    }

    public int executionModeCount() {
        return (int) VH_EXECUTION_MODE_COUNT.get(segment, 0L);
    }

    public MemorySegment executionModes() {
        return (MemorySegment) VH_EXECUTION_MODES.get(segment, 0L);
    }

    public SpvReflectEntryPoint_LocalSize localSize() {
        long size = SpvReflectEntryPoint_LocalSize.LAYOUT.byteSize();
        return new SpvReflectEntryPoint_LocalSize(segment.asSlice(LOCAL_SIZE_OFFSET, size));
    }

    public int invocations() {
        return (int) VH_INVOCATIONS.get(segment, 0L);
    }

    public int outputVertices() {
        return (int) VH_OUTPUT_VERTICES.get(segment, 0L);
    }

    public int resourceHeapAccessCount() {
        return (int) VH_RESOURCE_HEAP_ACCESS_COUNT.get(segment, 0L);
    }

    public SpvReflectEntryPointResourceHeapAccess resourceHeapAccess(int index) {
        MemorySegment base = (MemorySegment) VH_RESOURCE_HEAP_ACCESSES.get(segment, 0L);
        long elemSize = SpvReflectEntryPointResourceHeapAccess.LAYOUT.byteSize();
        return new SpvReflectEntryPointResourceHeapAccess(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectEntryPointResourceHeapAccess> resourceHeapAccesses() {
        int count = resourceHeapAccessCount();
        List<SpvReflectEntryPointResourceHeapAccess> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(resourceHeapAccess(i));
        }
        return list;
    }

    public int samplerHeapAccessCount() {
        return (int) VH_SAMPLER_HEAP_ACCESS_COUNT.get(segment, 0L);
    }

    public SpvReflectEntryPointSamplerHeapAccess samplerHeapAccess(int index) {
        MemorySegment base = (MemorySegment) VH_SAMPLER_HEAP_ACCESSES.get(segment, 0L);
        long elemSize = SpvReflectEntryPointSamplerHeapAccess.LAYOUT.byteSize();
        return new SpvReflectEntryPointSamplerHeapAccess(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectEntryPointSamplerHeapAccess> samplerHeapAccesses() {
        int count = samplerHeapAccessCount();
        List<SpvReflectEntryPointSamplerHeapAccess> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(samplerHeapAccess(i));
        }
        return list;
    }
}
