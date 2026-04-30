plugins {
    id("java-library")
}

dependencies {
    implementation(project(":annotations"))
    implementation(libs.javapoet)


    implementation(platform(libs.lwjgl.bom))
    implementation(libs.lwjgl.vulkan)

    compileOnly(libs.google.auto.service.annotations)
    annotationProcessor(libs.google.auto.service)
}