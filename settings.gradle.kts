rootProject.name = "vulkanic"

include("core")
include("annotations")
include("processor")

include("helpers:glfw")
include("helpers:sdl")
include("helpers:debug")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./libs.versions.toml"))
        }
    }
}