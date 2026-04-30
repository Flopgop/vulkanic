plugins {
    id("java-library")
    id("maven-publish")
}

dependencies {
    compileOnly(project(":annotations"))
    annotationProcessor(project(":processor"))

    compileOnlyApi(libs.jetbrains.annotations)
    api(libs.jspecify)

    api(libs.joml)
    api(platform(libs.lwjgl.bom))

    api(libs.lwjgl)
    api(libs.lwjgl.vma)
    api(libs.lwjgl.vulkan)

    implementation(variantOf(libs.lwjgl.natives) { classifier("natives-windows") })
    implementation(variantOf(libs.lwjgl.vma.natives) { classifier("natives-windows") })
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