package net.flamgop.vulkanic.reflect.bindings;

public enum SpvExecutionModel {
    SpvExecutionModelVertex(0),
    SpvExecutionModelTessellationControl(1),
    SpvExecutionModelTessellationEvaluation(2),
    SpvExecutionModelGeometry(3),
    SpvExecutionModelFragment(4),
    SpvExecutionModelGLCompute(5),
    SpvExecutionModelKernel(6),
    SpvExecutionModelTaskNV(5267),
    SpvExecutionModelMeshNV(5268),
    SpvExecutionModelRayGenerationKHR(5313),
    SpvExecutionModelRayGenerationNV(5313),
    SpvExecutionModelIntersectionKHR(5314),
    SpvExecutionModelIntersectionNV(5314),
    SpvExecutionModelAnyHitKHR(5315),
    SpvExecutionModelAnyHitNV(5315),
    SpvExecutionModelClosestHitKHR(5316),
    SpvExecutionModelClosestHitNV(5316),
    SpvExecutionModelMissKHR(5317),
    SpvExecutionModelMissNV(5317),
    SpvExecutionModelCallableKHR(5318),
    SpvExecutionModelCallableNV(5318),
    SpvExecutionModelTaskEXT(5364),
    SpvExecutionModelMeshEXT(5365),
    SpvExecutionModelMax(2147483647);

    private final int value;

    SpvExecutionModel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvExecutionModel fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvExecutionModel value: " + value);
    }
}
