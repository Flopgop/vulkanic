package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvLoopControlMask implements Bitmaskable<Integer> {
    SpvLoopControlMaskNone(0x00000000),
    SpvLoopControlUnrollMask(0x00000001),
    SpvLoopControlDontUnrollMask(0x00000002),
    SpvLoopControlDependencyInfiniteMask(0x00000004),
    SpvLoopControlDependencyLengthMask(0x00000008),
    SpvLoopControlMinIterationsMask(0x00000010),
    SpvLoopControlMaxIterationsMask(0x00000020),
    SpvLoopControlIterationMultipleMask(0x00000040),
    SpvLoopControlPeelCountMask(0x00000080),
    SpvLoopControlPartialCountMask(0x00000100),
    SpvLoopControlInitiationIntervalALTERAMask(0x00010000),
    SpvLoopControlInitiationIntervalINTELMask(0x00010000),
    SpvLoopControlMaxConcurrencyALTERAMask(0x00020000),
    SpvLoopControlMaxConcurrencyINTELMask(0x00020000),
    SpvLoopControlDependencyArrayALTERAMask(0x00040000),
    SpvLoopControlDependencyArrayINTELMask(0x00040000),
    SpvLoopControlPipelineEnableALTERAMask(0x00080000),
    SpvLoopControlPipelineEnableINTELMask(0x00080000),
    SpvLoopControlLoopCoalesceALTERAMask(0x00100000),
    SpvLoopControlLoopCoalesceINTELMask(0x00100000),
    SpvLoopControlMaxInterleavingALTERAMask(0x00200000),
    SpvLoopControlMaxInterleavingINTELMask(0x00200000),
    SpvLoopControlSpeculatedIterationsALTERAMask(0x00400000),
    SpvLoopControlSpeculatedIterationsINTELMask(0x00400000),
    SpvLoopControlNoFusionALTERAMask(0x00800000),
    SpvLoopControlNoFusionINTELMask(0x00800000),
    SpvLoopControlLoopCountALTERAMask(0x01000000),
    SpvLoopControlLoopCountINTELMask(0x01000000),
    SpvLoopControlMaxReinvocationDelayALTERAMask(0x02000000),
    SpvLoopControlMaxReinvocationDelayINTELMask(0x02000000),
    SpvLoopControlMultipleWaitQueuesQCOMMask(0x10000000);

    private final Integer flag;

    SpvLoopControlMask(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
