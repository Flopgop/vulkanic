rootProject.name = "vulkanic"

include("core")
include("annotations")
include("processor")

include("helpers:glfw")
include("helpers:sdl")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./libs.versions.toml"))
        }
    }
}