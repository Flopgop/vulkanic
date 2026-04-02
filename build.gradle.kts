plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.jetbrains.annotations)

    implementation(libs.joml)
    implementation(platform(libs.lwjgl.bom))

    implementation(libs.lwjgl)
    implementation(libs.lwjgl.glfw)
    implementation(libs.lwjgl.vma)
    implementation(libs.lwjgl.vulkan)

    implementation(variantOf(libs.lwjgl.natives) { classifier("natives-windows") })
    implementation(variantOf(libs.lwjgl.glfw.natives) { classifier("natives-windows") })
    implementation(variantOf(libs.lwjgl.vma.natives) { classifier("natives-windows") })
}