package net.flamgop.vulkanic.reflect.bindings;

public enum SpvGroupOperation {
    SpvGroupOperationReduce(0),
    SpvGroupOperationInclusiveScan(1),
    SpvGroupOperationExclusiveScan(2),
    SpvGroupOperationClusteredReduce(3),
    SpvGroupOperationPartitionedReduceEXT(6),
    SpvGroupOperationPartitionedReduceNV(6),
    SpvGroupOperationPartitionedInclusiveScanEXT(7),
    SpvGroupOperationPartitionedInclusiveScanNV(7),
    SpvGroupOperationPartitionedExclusiveScanEXT(8),
    SpvGroupOperationPartitionedExclusiveScanNV(8),
    SpvGroupOperationMax(2147483647);

    private final int value;

    SpvGroupOperation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvGroupOperation fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvGroupOperation value: " + value);
    }
}
