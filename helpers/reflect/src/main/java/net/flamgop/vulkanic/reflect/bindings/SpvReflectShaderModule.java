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

public final class SpvReflectShaderModule {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("generator"),
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("entry_point_name"),
            ValueLayout.JAVA_INT.withName("entry_point_id"),
            ValueLayout.JAVA_INT.withName("entry_point_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectEntryPoint.LAYOUT)).withName("entry_points"),
            ValueLayout.JAVA_INT.withName("source_language"),
            ValueLayout.JAVA_INT.withName("source_language_version"),
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("source_file"),
            ValueLayout.ADDRESS.withTargetLayout(Types.CSTRING).withName("source_source"),
            ValueLayout.JAVA_INT.withName("capability_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectCapability.LAYOUT)).withName("capabilities"),
            ValueLayout.JAVA_INT.withName("spirv_execution_model"),
            ValueLayout.JAVA_INT.withName("shader_stage"),
            ValueLayout.JAVA_INT.withName("descriptor_binding_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectDescriptorBinding.LAYOUT)).withName("descriptor_bindings"),
            ValueLayout.JAVA_INT.withName("descriptor_set_count"),
            MemoryLayout.sequenceLayout(64, SpvReflectDescriptorSet.LAYOUT).withName("descriptor_sets"),
            ValueLayout.JAVA_INT.withName("input_variable_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(ValueLayout.ADDRESS)).withName("input_variables"),
            ValueLayout.JAVA_INT.withName("output_variable_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(ValueLayout.ADDRESS)).withName("output_variables"),
            ValueLayout.JAVA_INT.withName("interface_variable_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectInterfaceVariable.LAYOUT)).withName("interface_variables"),
            ValueLayout.JAVA_INT.withName("push_constant_block_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectBlockVariable.LAYOUT)).withName("push_constant_blocks"),
            ValueLayout.JAVA_INT.withName("spec_constant_count"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectSpecializationConstant.LAYOUT)).withName("spec_constants"),
            ValueLayout.ADDRESS.withTargetLayout(Types.unboundedArray(SpvReflectShaderModule_Internal.LAYOUT)).withName("_internal")
    );

    private static final VarHandle VH_GENERATOR = LAYOUT.varHandle(PathElement.groupElement("generator"));
    private static final VarHandle VH_ENTRY_POINT_NAME = LAYOUT.varHandle(PathElement.groupElement("entry_point_name"));
    private static final VarHandle VH_ENTRY_POINT_ID = LAYOUT.varHandle(PathElement.groupElement("entry_point_id"));
    private static final VarHandle VH_ENTRY_POINT_COUNT = LAYOUT.varHandle(PathElement.groupElement("entry_point_count"));
    private static final VarHandle VH_ENTRY_POINTS = LAYOUT.varHandle(PathElement.groupElement("entry_points"));
    private static final VarHandle VH_SOURCE_LANGUAGE = LAYOUT.varHandle(PathElement.groupElement("source_language"));
    private static final VarHandle VH_SOURCE_LANGUAGE_VERSION = LAYOUT.varHandle(PathElement.groupElement("source_language_version"));
    private static final VarHandle VH_SOURCE_FILE = LAYOUT.varHandle(PathElement.groupElement("source_file"));
    private static final VarHandle VH_SOURCE_SOURCE = LAYOUT.varHandle(PathElement.groupElement("source_source"));
    private static final VarHandle VH_CAPABILITY_COUNT = LAYOUT.varHandle(PathElement.groupElement("capability_count"));
    private static final VarHandle VH_CAPABILITIES = LAYOUT.varHandle(PathElement.groupElement("capabilities"));
    private static final VarHandle VH_SPIRV_EXECUTION_MODEL = LAYOUT.varHandle(PathElement.groupElement("spirv_execution_model"));
    private static final VarHandle VH_SHADER_STAGE = LAYOUT.varHandle(PathElement.groupElement("shader_stage"));
    private static final VarHandle VH_DESCRIPTOR_BINDING_COUNT = LAYOUT.varHandle(PathElement.groupElement("descriptor_binding_count"));
    private static final VarHandle VH_DESCRIPTOR_BINDINGS = LAYOUT.varHandle(PathElement.groupElement("descriptor_bindings"));
    private static final VarHandle VH_DESCRIPTOR_SET_COUNT = LAYOUT.varHandle(PathElement.groupElement("descriptor_set_count"));
    private static final VarHandle VH_INPUT_VARIABLE_COUNT = LAYOUT.varHandle(PathElement.groupElement("input_variable_count"));
    private static final VarHandle VH_INPUT_VARIABLES = LAYOUT.varHandle(PathElement.groupElement("input_variables"));
    private static final VarHandle VH_OUTPUT_VARIABLE_COUNT = LAYOUT.varHandle(PathElement.groupElement("output_variable_count"));
    private static final VarHandle VH_OUTPUT_VARIABLES = LAYOUT.varHandle(PathElement.groupElement("output_variables"));
    private static final VarHandle VH_INTERFACE_VARIABLE_COUNT = LAYOUT.varHandle(PathElement.groupElement("interface_variable_count"));
    private static final VarHandle VH_INTERFACE_VARIABLES = LAYOUT.varHandle(PathElement.groupElement("interface_variables"));
    private static final VarHandle VH_PUSH_CONSTANT_BLOCK_COUNT = LAYOUT.varHandle(PathElement.groupElement("push_constant_block_count"));
    private static final VarHandle VH_PUSH_CONSTANT_BLOCKS = LAYOUT.varHandle(PathElement.groupElement("push_constant_blocks"));
    private static final VarHandle VH_SPEC_CONSTANT_COUNT = LAYOUT.varHandle(PathElement.groupElement("spec_constant_count"));
    private static final VarHandle VH_SPEC_CONSTANTS = LAYOUT.varHandle(PathElement.groupElement("spec_constants"));
    private static final VarHandle VH_INTERNAL = LAYOUT.varHandle(PathElement.groupElement("_internal"));

    private static final long DESCRIPTOR_SETS_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("descriptor_sets"));

    private final MemorySegment segment;

    
    public SpvReflectShaderModule(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectShaderModule(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public SpvReflectGenerator generator() {
        return SpvReflectGenerator.fromValue((int) VH_GENERATOR.get(segment, 0L));
    }

    
    public String entryPointName() {
        MemorySegment ptr = (MemorySegment) VH_ENTRY_POINT_NAME.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return ptr.getString(0);
    }

    public int entryPointId() {
        return (int) VH_ENTRY_POINT_ID.get(segment, 0L);
    }

    public int entryPointCount() {
        return (int) VH_ENTRY_POINT_COUNT.get(segment, 0L);
    }

    public SpvReflectEntryPoint entryPoint(int index) {
        MemorySegment base = (MemorySegment) VH_ENTRY_POINTS.get(segment, 0L);
        long elemSize = SpvReflectEntryPoint.LAYOUT.byteSize();
        return new SpvReflectEntryPoint(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectEntryPoint> entryPoints() {
        int count = entryPointCount();
        List<SpvReflectEntryPoint> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(entryPoint(i));
        }
        return list;
    }

    public SpvSourceLanguage sourceLanguage() {
        return SpvSourceLanguage.fromValue((int) VH_SOURCE_LANGUAGE.get(segment, 0L));
    }

    public int sourceLanguageVersion() {
        return (int) VH_SOURCE_LANGUAGE_VERSION.get(segment, 0L);
    }

    
    public String sourceFile() {
        MemorySegment ptr = (MemorySegment) VH_SOURCE_FILE.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return ptr.getString(0);
    }

    
    public String sourceSource() {
        MemorySegment ptr = (MemorySegment) VH_SOURCE_SOURCE.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return ptr.getString(0);
    }

    public int capabilityCount() {
        return (int) VH_CAPABILITY_COUNT.get(segment, 0L);
    }

    public SpvReflectCapability capability(int index) {
        MemorySegment base = (MemorySegment) VH_CAPABILITIES.get(segment, 0L);
        long elemSize = SpvReflectCapability.LAYOUT.byteSize();
        return new SpvReflectCapability(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectCapability> capabilities() {
        int count = capabilityCount();
        List<SpvReflectCapability> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(capability(i));
        }
        return list;
    }

    public SpvExecutionModel spirvExecutionModel() {
        return SpvExecutionModel.fromValue((int) VH_SPIRV_EXECUTION_MODEL.get(segment, 0L));
    }

    public EnumIntBitset<SpvReflectShaderStageFlagBits> shaderStage() {
        return new EnumIntBitset<>((int) VH_SHADER_STAGE.get(segment, 0L));
    }

    public int descriptorBindingCount() {
        return (int) VH_DESCRIPTOR_BINDING_COUNT.get(segment, 0L);
    }

    public SpvReflectDescriptorBinding descriptorBinding(int index) {
        MemorySegment base = (MemorySegment) VH_DESCRIPTOR_BINDINGS.get(segment, 0L);
        long elemSize = SpvReflectDescriptorBinding.LAYOUT.byteSize();
        return new SpvReflectDescriptorBinding(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectDescriptorBinding> descriptorBindings() {
        int count = descriptorBindingCount();
        List<SpvReflectDescriptorBinding> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(descriptorBinding(i));
        }
        return list;
    }

    public int descriptorSetCount() {
        return (int) VH_DESCRIPTOR_SET_COUNT.get(segment, 0L);
    }

    public SpvReflectDescriptorSet descriptorSet(int index) {
        long elemSize = SpvReflectDescriptorSet.LAYOUT.byteSize();
        return new SpvReflectDescriptorSet(segment.asSlice(DESCRIPTOR_SETS_OFFSET + (long) index * elemSize, elemSize));
    }

    public List<SpvReflectDescriptorSet> descriptorSets() {
        int count = descriptorSetCount();
        List<SpvReflectDescriptorSet> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(descriptorSet(i));
        }
        return list;
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

    public int pushConstantBlockCount() {
        return (int) VH_PUSH_CONSTANT_BLOCK_COUNT.get(segment, 0L);
    }

    public SpvReflectBlockVariable pushConstantBlock(int index) {
        MemorySegment base = (MemorySegment) VH_PUSH_CONSTANT_BLOCKS.get(segment, 0L);
        long elemSize = SpvReflectBlockVariable.LAYOUT.byteSize();
        return new SpvReflectBlockVariable(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectBlockVariable> pushConstantBlocks() {
        int count = pushConstantBlockCount();
        List<SpvReflectBlockVariable> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(pushConstantBlock(i));
        }
        return list;
    }

    public int specConstantCount() {
        return (int) VH_SPEC_CONSTANT_COUNT.get(segment, 0L);
    }

    public SpvReflectSpecializationConstant specConstant(int index) {
        MemorySegment base = (MemorySegment) VH_SPEC_CONSTANTS.get(segment, 0L);
        long elemSize = SpvReflectSpecializationConstant.LAYOUT.byteSize();
        return new SpvReflectSpecializationConstant(base.asSlice((long) index * elemSize, elemSize));
    }

    public List<SpvReflectSpecializationConstant> specConstants() {
        int count = specConstantCount();
        List<SpvReflectSpecializationConstant> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(specConstant(i));
        }
        return list;
    }

    
    public SpvReflectShaderModule_Internal internal() {
        MemorySegment ptr = (MemorySegment) VH_INTERNAL.get(segment, 0L);
        if (ptr.equals(MemorySegment.NULL)) return null;
        return new SpvReflectShaderModule_Internal(ptr);
    }
}
