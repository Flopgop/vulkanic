package net.flamgop.vulkanic.memory.image;

public record VulkanicComponentMapping(VulkanicSwizzle r, VulkanicSwizzle g, VulkanicSwizzle b, VulkanicSwizzle a) {
    public static VulkanicComponentMapping identity() { return new VulkanicComponentMapping(VulkanicSwizzle.IDENTITY, VulkanicSwizzle.IDENTITY, VulkanicSwizzle.IDENTITY, VulkanicSwizzle.IDENTITY); }
    public static VulkanicComponentMapping zero() { return new VulkanicComponentMapping(VulkanicSwizzle.ZERO, VulkanicSwizzle.ZERO, VulkanicSwizzle.ZERO, VulkanicSwizzle.ZERO); }
    public static VulkanicComponentMapping one() { return new VulkanicComponentMapping(VulkanicSwizzle.ONE, VulkanicSwizzle.ONE, VulkanicSwizzle.ONE, VulkanicSwizzle.ONE); }
}
