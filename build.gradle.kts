plugins {
    id("java-library")
    id("maven-publish")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.jetbrains.annotations)

    api(libs.joml)
    implementation(platform(libs.lwjgl.bom))

    api(libs.lwjgl)
    api(libs.lwjgl.glfw)
    api(libs.lwjgl.vma)
    api(libs.lwjgl.vulkan)

    implementation(variantOf(libs.lwjgl.natives) { classifier("natives-windows") })
    implementation(variantOf(libs.lwjgl.glfw.natives) { classifier("natives-windows") })
    implementation(variantOf(libs.lwjgl.vma.natives) { classifier("natives-windows") })
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "net.flamgop"
            artifactId = "vulkanic"
            version = "0.0.1"
        }
    }
}