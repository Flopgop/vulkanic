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
    compileOnly(project(":annotations"))
    annotationProcessor(project(":processor"))

    compileOnlyApi(libs.jetbrains.annotations)
    implementation(libs.jspecify)

    api(libs.joml)
    api(platform(libs.lwjgl.bom))

    api(libs.lwjgl)
    api(libs.lwjgl.vma)
    api(libs.lwjgl.vulkan)

    lwjglNativeTargets.forEach { c ->
        implementation(variantOf(libs.lwjgl.natives) { classifier(c) })
        implementation(variantOf(libs.lwjgl.vma.natives) { classifier(c) })
    }
}

sourceSets {
    main {
        java {
            srcDir("build/generated/sources/annotationProcessor/java/main")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "net.flamgop.vulkanic"
            artifactId = "core"
            version = "0.0.1"
        }
    }
}