package net.flamgop.vulkanic.reflect.bindings;

public enum SpvReflectGenerator {
    SPV_REFLECT_GENERATOR_KHRONOS_LLVM_SPIRV_TRANSLATOR(6),
    SPV_REFLECT_GENERATOR_KHRONOS_SPIRV_TOOLS_ASSEMBLER(7),
    SPV_REFLECT_GENERATOR_KHRONOS_GLSLANG_REFERENCE_FRONT_END(8),
    SPV_REFLECT_GENERATOR_GOOGLE_SHADERC_OVER_GLSLANG(13),
    SPV_REFLECT_GENERATOR_GOOGLE_SPIREGG(14),
    SPV_REFLECT_GENERATOR_GOOGLE_RSPIRV(15),
    SPV_REFLECT_GENERATOR_X_LEGEND_MESA_MESAIR_SPIRV_TRANSLATOR(16),
    SPV_REFLECT_GENERATOR_KHRONOS_SPIRV_TOOLS_LINKER(17),
    SPV_REFLECT_GENERATOR_WINE_VKD3D_SHADER_COMPILER(18),
    SPV_REFLECT_GENERATOR_CLAY_CLAY_SHADER_COMPILER(19),
    SPV_REFLECT_GENERATOR_SLANG_SHADER_COMPILER(40);

    private final int value;

    SpvReflectGenerator(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvReflectGenerator fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvReflectGenerator value: " + value);
    }
}
