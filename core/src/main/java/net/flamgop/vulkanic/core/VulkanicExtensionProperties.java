package net.flamgop.vulkanic.core;

/// A simple extension descriptor
/// @param name the name of the extension, e.g., VK_MESH_SHADER_EXT
/// @param specVersion the specific spec version supported by this driver.
public record VulkanicExtensionProperties(String name, int specVersion) {
}
