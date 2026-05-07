plugins {
    id("java-library")
    id("maven-publish")
}

val lwjglNativeTargets = listOf(
    "natives-linux",
    "natives-linux-arm64",
    "natives-macos",
    "natives-macos-arm64",
    "natives-windows",
    "natives-windows-arm64"
)

dependencies {
    implementation(project(":core"))

    api(libs.lwjgl.sdl)
    lwjglNativeTargets.forEach { c ->
        implementation(variantOf(libs.lwjgl.sdl.natives) { classifier(c) })
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "net.flamgop.vulkanic"
            artifactId = "helpers-sdl"
            version = "0.0.1"
        }
    }
}