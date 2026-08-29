package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.EnumIntBitset;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import static java.lang.foreign.ValueLayout.*;

public final class SpvReflect implements AutoCloseable {

    private final Arena arena;
    private final SymbolLookup lookup;
    private final Linker linker;

    private final MethodHandle spvReflectCreateShaderModule;
    private final MethodHandle spvReflectCreateShaderModule2;
    private final MethodHandle spvReflectGetShaderModule;
    private final MethodHandle spvReflectDestroyShaderModule;
    private final MethodHandle spvReflectGetCodeSize;
    private final MethodHandle spvReflectGetCode;
    private final MethodHandle spvReflectGetEntryPoint;
    private final MethodHandle spvReflectEnumerateDescriptorBindings;
    private final MethodHandle spvReflectEnumerateEntryPointDescriptorBindings;
    private final MethodHandle spvReflectEnumerateDescriptorSets;
    private final MethodHandle spvReflectEnumerateEntryPointDescriptorSets;
    private final MethodHandle spvReflectEnumerateInterfaceVariables;
    private final MethodHandle spvReflectEnumerateEntryPointInterfaceVariables;
    private final MethodHandle spvReflectEnumerateInputVariables;
    private final MethodHandle spvReflectEnumerateEntryPointInputVariables;
    private final MethodHandle spvReflectEnumerateOutputVariables;
    private final MethodHandle spvReflectEnumerateEntryPointOutputVariables;
    private final MethodHandle spvReflectEnumeratePushConstantBlocks;
    private final MethodHandle spvReflectEnumeratePushConstants;
    private final MethodHandle spvReflectEnumerateEntryPointPushConstantBlocks;
    private final MethodHandle spvReflectEnumerateSpecializationConstants;
    private final MethodHandle spvReflectGetDescriptorBinding;
    private final MethodHandle spvReflectGetEntryPointDescriptorBinding;
    private final MethodHandle spvReflectGetDescriptorSet;
    private final MethodHandle spvReflectGetEntryPointDescriptorSet;
    private final MethodHandle spvReflectGetInputVariableByLocation;
    private final MethodHandle spvReflectGetInputVariable;
    private final MethodHandle spvReflectGetEntryPointInputVariableByLocation;
    private final MethodHandle spvReflectGetInputVariableBySemantic;
    private final MethodHandle spvReflectGetEntryPointInputVariableBySemantic;
    private final MethodHandle spvReflectGetOutputVariableByLocation;
    private final MethodHandle spvReflectGetOutputVariable;
    private final MethodHandle spvReflectGetEntryPointOutputVariableByLocation;
    private final MethodHandle spvReflectGetOutputVariableBySemantic;
    private final MethodHandle spvReflectGetEntryPointOutputVariableBySemantic;
    private final MethodHandle spvReflectGetPushConstantBlock;
    private final MethodHandle spvReflectGetPushConstant;
    private final MethodHandle spvReflectGetEntryPointPushConstantBlock;
    private final MethodHandle spvReflectChangeDescriptorBindingNumbers;
    private final MethodHandle spvReflectChangeDescriptorBindingNumber;
    private final MethodHandle spvReflectChangeDescriptorSetNumber;
    private final MethodHandle spvReflectChangeInputVariableLocation;
    private final MethodHandle spvReflectChangeOutputVariableLocation;
    private final MethodHandle spvReflectSourceLanguage;
    private final MethodHandle spvReflectBlockVariableTypeName;

    public SpvReflect(Path libraryPath) {
        this.arena = Arena.ofShared();
        this.lookup = SymbolLookup.libraryLookup(libraryPath, arena);
        this.linker = Linker.nativeLinker();

        this.spvReflectCreateShaderModule = handle("spvReflectCreateShaderModule", FunctionDescriptor.of(JAVA_INT, JAVA_LONG, ADDRESS, ADDRESS));
        this.spvReflectCreateShaderModule2 = handle("spvReflectCreateShaderModule2", FunctionDescriptor.of(JAVA_INT, JAVA_INT, JAVA_LONG, ADDRESS, ADDRESS));
        this.spvReflectGetShaderModule = handle("spvReflectGetShaderModule", FunctionDescriptor.of(JAVA_INT, JAVA_LONG, ADDRESS, ADDRESS));
        this.spvReflectDestroyShaderModule = handle("spvReflectDestroyShaderModule", FunctionDescriptor.ofVoid(ADDRESS));
        this.spvReflectGetCodeSize = handle("spvReflectGetCodeSize", FunctionDescriptor.of(JAVA_INT, ADDRESS));
        this.spvReflectGetCode = handle("spvReflectGetCode", FunctionDescriptor.of(ADDRESS, ADDRESS));
        this.spvReflectGetEntryPoint = handle("spvReflectGetEntryPoint", FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateDescriptorBindings = handle("spvReflectEnumerateDescriptorBindings", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateEntryPointDescriptorBindings = handle("spvReflectEnumerateEntryPointDescriptorBindings", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateDescriptorSets = handle("spvReflectEnumerateDescriptorSets", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateEntryPointDescriptorSets = handle("spvReflectEnumerateEntryPointDescriptorSets", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateInterfaceVariables = handle("spvReflectEnumerateInterfaceVariables", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateEntryPointInterfaceVariables = handle("spvReflectEnumerateEntryPointInterfaceVariables", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateInputVariables = handle("spvReflectEnumerateInputVariables", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateEntryPointInputVariables = handle("spvReflectEnumerateEntryPointInputVariables", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateOutputVariables = handle("spvReflectEnumerateOutputVariables", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateEntryPointOutputVariables = handle("spvReflectEnumerateEntryPointOutputVariables", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumeratePushConstantBlocks = handle("spvReflectEnumeratePushConstantBlocks", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumeratePushConstants = handle("spvReflectEnumeratePushConstants", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateEntryPointPushConstantBlocks = handle("spvReflectEnumerateEntryPointPushConstantBlocks", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectEnumerateSpecializationConstants = handle("spvReflectEnumerateSpecializationConstants", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectGetDescriptorBinding = handle("spvReflectGetDescriptorBinding", FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT, JAVA_INT, ADDRESS));
        this.spvReflectGetEntryPointDescriptorBinding = handle("spvReflectGetEntryPointDescriptorBinding", FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, JAVA_INT, JAVA_INT, ADDRESS));
        this.spvReflectGetDescriptorSet = handle("spvReflectGetDescriptorSet", FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT, ADDRESS));
        this.spvReflectGetEntryPointDescriptorSet = handle("spvReflectGetEntryPointDescriptorSet", FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, JAVA_INT, ADDRESS));
        this.spvReflectGetInputVariableByLocation = handle("spvReflectGetInputVariableByLocation", FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT, ADDRESS));
        this.spvReflectGetInputVariable = handle("spvReflectGetInputVariable", FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT, ADDRESS));
        this.spvReflectGetEntryPointInputVariableByLocation = handle("spvReflectGetEntryPointInputVariableByLocation", FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, JAVA_INT, ADDRESS));
        this.spvReflectGetInputVariableBySemantic = handle("spvReflectGetInputVariableBySemantic", FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectGetEntryPointInputVariableBySemantic = handle("spvReflectGetEntryPointInputVariableBySemantic", FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectGetOutputVariableByLocation = handle("spvReflectGetOutputVariableByLocation", FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT, ADDRESS));
        this.spvReflectGetOutputVariable = handle("spvReflectGetOutputVariable", FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT, ADDRESS));
        this.spvReflectGetEntryPointOutputVariableByLocation = handle("spvReflectGetEntryPointOutputVariableByLocation", FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, JAVA_INT, ADDRESS));
        this.spvReflectGetOutputVariableBySemantic = handle("spvReflectGetOutputVariableBySemantic", FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectGetEntryPointOutputVariableBySemantic = handle("spvReflectGetEntryPointOutputVariableBySemantic", FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectGetPushConstantBlock = handle("spvReflectGetPushConstantBlock", FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT, ADDRESS));
        this.spvReflectGetPushConstant = handle("spvReflectGetPushConstant", FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT, ADDRESS));
        this.spvReflectGetEntryPointPushConstantBlock = handle("spvReflectGetEntryPointPushConstantBlock", FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, ADDRESS));
        this.spvReflectChangeDescriptorBindingNumbers = handle("spvReflectChangeDescriptorBindingNumbers", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, JAVA_INT, JAVA_INT));
        this.spvReflectChangeDescriptorBindingNumber = handle("spvReflectChangeDescriptorBindingNumber", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, JAVA_INT, JAVA_INT));
        this.spvReflectChangeDescriptorSetNumber = handle("spvReflectChangeDescriptorSetNumber", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, JAVA_INT));
        this.spvReflectChangeInputVariableLocation = handle("spvReflectChangeInputVariableLocation", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, JAVA_INT));
        this.spvReflectChangeOutputVariableLocation = handle("spvReflectChangeOutputVariableLocation", FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, JAVA_INT));
        this.spvReflectSourceLanguage = handle("spvReflectSourceLanguage", FunctionDescriptor.of(ADDRESS, JAVA_INT));
        this.spvReflectBlockVariableTypeName = handle("spvReflectBlockVariableTypeName", FunctionDescriptor.of(ADDRESS, ADDRESS));
    }

    private MethodHandle handle(String name, FunctionDescriptor descriptor) {
        MemorySegment symbol = lookup.find(name).orElseThrow(() -> new NoSuchElementException(name));
        return linker.downcallHandle(symbol, descriptor);
    }

    private static RuntimeException wrap(Throwable t) {
        return new RuntimeException(t);
    }

    public SpvReflectResult spvReflectCreateShaderModule(long size, MemorySegment pCode, SpvReflectShaderModule pModule) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectCreateShaderModule.invokeExact(size, pCode, pModule.segment()));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectCreateShaderModule2(EnumIntBitset<SpvReflectModuleFlagBits> flags, long size, MemorySegment pCode, SpvReflectShaderModule pModule) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectCreateShaderModule2.invokeExact(flags.mask(), size, pCode, pModule.segment()));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectGetShaderModule(long size, MemorySegment pCode, SpvReflectShaderModule pModule) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectGetShaderModule.invokeExact(size, pCode, pModule.segment()));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void spvReflectDestroyShaderModule(SpvReflectShaderModule pModule) {
        try {
            spvReflectDestroyShaderModule.invokeExact(pModule.segment());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    public int spvReflectGetCodeSize(SpvReflectShaderModule pModule) {
        try {
            return (int) spvReflectGetCodeSize.invokeExact(pModule.segment());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    public MemorySegment spvReflectGetCode(SpvReflectShaderModule pModule) {
        try {
            return (MemorySegment) spvReflectGetCode.invokeExact(pModule.segment());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectEntryPoint spvReflectGetEntryPoint(SpvReflectShaderModule pModule, MemorySegment entryPoint) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetEntryPoint.invokeExact(pModule.segment(), entryPoint);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectEntryPoint(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    public SpvReflectResult spvReflectEnumerateDescriptorBindings(SpvReflectShaderModule pModule, MemorySegment pCount, MemorySegment ppBindings) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateDescriptorBindings.invokeExact(pModule.segment(), pCount, ppBindings));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    public SpvReflectResult spvReflectEnumerateEntryPointDescriptorBindings(SpvReflectShaderModule pModule, MemorySegment entryPoint, MemorySegment pCount, MemorySegment ppBindings) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateEntryPointDescriptorBindings.invokeExact(pModule.segment(), entryPoint, pCount, ppBindings));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    public SpvReflectResult spvReflectEnumerateDescriptorSets(SpvReflectShaderModule pModule, MemorySegment pCount, MemorySegment ppSets) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateDescriptorSets.invokeExact(pModule.segment(), pCount, ppSets));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    public SpvReflectResult spvReflectEnumerateEntryPointDescriptorSets(SpvReflectShaderModule pModule, MemorySegment entryPoint, MemorySegment pCount, MemorySegment ppSets) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateEntryPointDescriptorSets.invokeExact(pModule.segment(), entryPoint, pCount, ppSets));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectEnumerateInterfaceVariables(SpvReflectShaderModule pModule, MemorySegment pCount, MemorySegment ppVariables) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateInterfaceVariables.invokeExact(pModule.segment(), pCount, ppVariables));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    public SpvReflectResult spvReflectEnumerateEntryPointInterfaceVariables(SpvReflectShaderModule pModule, MemorySegment entryPoint, MemorySegment pCount, MemorySegment ppVariables) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateEntryPointInterfaceVariables.invokeExact(pModule.segment(), entryPoint, pCount, ppVariables));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    public SpvReflectResult spvReflectEnumerateInputVariables(SpvReflectShaderModule pModule, MemorySegment pCount, MemorySegment ppVariables) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateInputVariables.invokeExact(pModule.segment(), pCount, ppVariables));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    public SpvReflectResult spvReflectEnumerateEntryPointInputVariables(SpvReflectShaderModule pModule, MemorySegment entryPoint, MemorySegment pCount, MemorySegment ppVariables) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateEntryPointInputVariables.invokeExact(pModule.segment(), entryPoint, pCount, ppVariables));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    public SpvReflectResult spvReflectEnumerateOutputVariables(SpvReflectShaderModule pModule, MemorySegment pCount, MemorySegment ppVariables) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateOutputVariables.invokeExact(pModule.segment(), pCount, ppVariables));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectEnumerateEntryPointOutputVariables(SpvReflectShaderModule pModule, MemorySegment entryPoint, MemorySegment pCount, MemorySegment ppVariables) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateEntryPointOutputVariables.invokeExact(pModule.segment(), entryPoint, pCount, ppVariables));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectEnumeratePushConstantBlocks(SpvReflectShaderModule pModule, MemorySegment pCount, MemorySegment ppBlocks) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumeratePushConstantBlocks.invokeExact(pModule.segment(), pCount, ppBlocks));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectEnumeratePushConstants(SpvReflectShaderModule pModule, MemorySegment pCount, MemorySegment ppBlocks) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumeratePushConstants.invokeExact(pModule.segment(), pCount, ppBlocks));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectEnumerateEntryPointPushConstantBlocks(SpvReflectShaderModule pModule, MemorySegment entryPoint, MemorySegment pCount, MemorySegment ppBlocks) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateEntryPointPushConstantBlocks.invokeExact(pModule.segment(), entryPoint, pCount, ppBlocks));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectEnumerateSpecializationConstants(SpvReflectShaderModule pModule, MemorySegment pCount, MemorySegment ppConstants) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectEnumerateSpecializationConstants.invokeExact(pModule.segment(), pCount, ppConstants));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectDescriptorBinding spvReflectGetDescriptorBinding(SpvReflectShaderModule pModule, int bindingNumber, int setNumber, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetDescriptorBinding.invokeExact(pModule.segment(), bindingNumber, setNumber, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectDescriptorBinding(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectDescriptorBinding spvReflectGetEntryPointDescriptorBinding(SpvReflectShaderModule pModule, MemorySegment entryPoint, int bindingNumber, int setNumber, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetEntryPointDescriptorBinding.invokeExact(pModule.segment(), entryPoint, bindingNumber, setNumber, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectDescriptorBinding(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectDescriptorSet spvReflectGetDescriptorSet(SpvReflectShaderModule pModule, int setNumber, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetDescriptorSet.invokeExact(pModule.segment(), setNumber, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectDescriptorSet(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectDescriptorSet spvReflectGetEntryPointDescriptorSet(SpvReflectShaderModule pModule, MemorySegment entryPoint, int setNumber, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetEntryPointDescriptorSet.invokeExact(pModule.segment(), entryPoint, setNumber, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectDescriptorSet(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectInterfaceVariable spvReflectGetInputVariableByLocation(SpvReflectShaderModule pModule, int location, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetInputVariableByLocation.invokeExact(pModule.segment(), location, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectInterfaceVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectInterfaceVariable spvReflectGetInputVariable(SpvReflectShaderModule pModule, int location, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetInputVariable.invokeExact(pModule.segment(), location, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectInterfaceVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectInterfaceVariable spvReflectGetEntryPointInputVariableByLocation(SpvReflectShaderModule pModule, MemorySegment entryPoint, int location, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetEntryPointInputVariableByLocation.invokeExact(pModule.segment(), entryPoint, location, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectInterfaceVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectInterfaceVariable spvReflectGetInputVariableBySemantic(SpvReflectShaderModule pModule, MemorySegment semantic, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetInputVariableBySemantic.invokeExact(pModule.segment(), semantic, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectInterfaceVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectInterfaceVariable spvReflectGetEntryPointInputVariableBySemantic(SpvReflectShaderModule pModule, MemorySegment entryPoint, MemorySegment semantic, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetEntryPointInputVariableBySemantic.invokeExact(pModule.segment(), entryPoint, semantic, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectInterfaceVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectInterfaceVariable spvReflectGetOutputVariableByLocation(SpvReflectShaderModule pModule, int location, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetOutputVariableByLocation.invokeExact(pModule.segment(), location, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectInterfaceVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectInterfaceVariable spvReflectGetOutputVariable(SpvReflectShaderModule pModule, int location, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetOutputVariable.invokeExact(pModule.segment(), location, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectInterfaceVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectInterfaceVariable spvReflectGetEntryPointOutputVariableByLocation(SpvReflectShaderModule pModule, MemorySegment entryPoint, int location, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetEntryPointOutputVariableByLocation.invokeExact(pModule.segment(), entryPoint, location, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectInterfaceVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectInterfaceVariable spvReflectGetOutputVariableBySemantic(SpvReflectShaderModule pModule, MemorySegment semantic, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetOutputVariableBySemantic.invokeExact(pModule.segment(), semantic, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectInterfaceVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectInterfaceVariable spvReflectGetEntryPointOutputVariableBySemantic(SpvReflectShaderModule pModule, MemorySegment entryPoint, MemorySegment semantic, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetEntryPointOutputVariableBySemantic.invokeExact(pModule.segment(), entryPoint, semantic, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectInterfaceVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectBlockVariable spvReflectGetPushConstantBlock(SpvReflectShaderModule pModule, int index, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetPushConstantBlock.invokeExact(pModule.segment(), index, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectBlockVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectBlockVariable spvReflectGetPushConstant(SpvReflectShaderModule pModule, int index, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetPushConstant.invokeExact(pModule.segment(), index, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectBlockVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectBlockVariable spvReflectGetEntryPointPushConstantBlock(SpvReflectShaderModule pModule, MemorySegment entryPoint, MemorySegment pResult) {
        try {
            MemorySegment r = (MemorySegment) spvReflectGetEntryPointPushConstantBlock.invokeExact(pModule.segment(), entryPoint, pResult);
            if (r.equals(MemorySegment.NULL)) return null;
            return new SpvReflectBlockVariable(r);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectChangeDescriptorBindingNumbers(SpvReflectShaderModule pModule, SpvReflectDescriptorBinding pBinding, int newBindingNumber, int newSetNumber) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectChangeDescriptorBindingNumbers.invokeExact(pModule.segment(), pBinding.segment(), newBindingNumber, newSetNumber));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectChangeDescriptorBindingNumber(SpvReflectShaderModule pModule, SpvReflectDescriptorBinding pDescriptorBinding, int newBindingNumber, int optionalNewSetNumber) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectChangeDescriptorBindingNumber.invokeExact(pModule.segment(), pDescriptorBinding.segment(), newBindingNumber, optionalNewSetNumber));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectChangeDescriptorSetNumber(SpvReflectShaderModule pModule, SpvReflectDescriptorSet pSet, int newSetNumber) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectChangeDescriptorSetNumber.invokeExact(pModule.segment(), pSet.segment(), newSetNumber));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectChangeInputVariableLocation(SpvReflectShaderModule pModule, SpvReflectInterfaceVariable pInputVariable, int newLocation) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectChangeInputVariableLocation.invokeExact(pModule.segment(), pInputVariable.segment(), newLocation));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public SpvReflectResult spvReflectChangeOutputVariableLocation(SpvReflectShaderModule pModule, SpvReflectInterfaceVariable pOutputVariable, int newLocation) {
        try {
            return SpvReflectResult.fromValue((int) spvReflectChangeOutputVariableLocation.invokeExact(pModule.segment(), pOutputVariable.segment(), newLocation));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public MemorySegment spvReflectSourceLanguage(SpvSourceLanguage sourceLang) {
        try {
            return (MemorySegment) spvReflectSourceLanguage.invokeExact(sourceLang.getValue());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public MemorySegment spvReflectBlockVariableTypeName(SpvReflectBlockVariable pVar) {
        try {
            return (MemorySegment) spvReflectBlockVariableTypeName.invokeExact(pVar.segment());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void close() {
        arena.close();
    }
}
