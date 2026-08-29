rootProject.name = "vulkanic"

include("core")
include("annotations")
include("processor")

include("helpers:glfw")
include("helpers:sdl")
include("helpers:debug")
include("helpers:reflect")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./libs.versions.toml"))
        }
    }
}
