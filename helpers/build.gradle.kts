plugins {
    id("java-library")
    id("maven-publish")
}

dependencies {
    implementation(project(":core"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "net.flamgop.vulkanic"
            artifactId = "helpers"
            version = "0.0.1"
        }
    }
}