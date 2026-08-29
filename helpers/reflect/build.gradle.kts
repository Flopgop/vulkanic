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
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "net.flamgop.vulkanic"
            artifactId = "helpers-reflect"
            version = "0.0.1"
        }
    }
}