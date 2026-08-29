package net.flamgop.vulkanic.reflect.bindings;

public enum SpvLoopControlShift {
    SpvLoopControlUnrollShift(0),
    SpvLoopControlDontUnrollShift(1),
    SpvLoopControlDependencyInfiniteShift(2),
    SpvLoopControlDependencyLengthShift(3),
    SpvLoopControlMinIterationsShift(4),
    SpvLoopControlMaxIterationsShift(5),
    SpvLoopControlIterationMultipleShift(6),
    SpvLoopControlPeelCountShift(7),
    SpvLoopControlPartialCountShift(8),
    SpvLoopControlInitiationIntervalALTERAShift(16),
    SpvLoopControlInitiationIntervalINTELShift(16),
    SpvLoopControlMaxConcurrencyALTERAShift(17),
    SpvLoopControlMaxConcurrencyINTELShift(17),
    SpvLoopControlDependencyArrayALTERAShift(18),
    SpvLoopControlDependencyArrayINTELShift(18),
    SpvLoopControlPipelineEnableALTERAShift(19),
    SpvLoopControlPipelineEnableINTELShift(19),
    SpvLoopControlLoopCoalesceALTERAShift(20),
    SpvLoopControlLoopCoalesceINTELShift(20),
    SpvLoopControlMaxInterleavingALTERAShift(21),
    SpvLoopControlMaxInterleavingINTELShift(21),
    SpvLoopControlSpeculatedIterationsALTERAShift(22),
    SpvLoopControlSpeculatedIterationsINTELShift(22),
    SpvLoopControlNoFusionALTERAShift(23),
    SpvLoopControlNoFusionINTELShift(23),
    SpvLoopControlLoopCountALTERAShift(24),
    SpvLoopControlLoopCountINTELShift(24),
    SpvLoopControlMaxReinvocationDelayALTERAShift(25),
    SpvLoopControlMaxReinvocationDelayINTELShift(25),
    SpvLoopControlMultipleWaitQueuesQCOMShift(28),
    SpvLoopControlMax(2147483647);

    private final int value;

    SpvLoopControlShift(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvLoopControlShift fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvLoopControlShift value: " + value);
    }
}
