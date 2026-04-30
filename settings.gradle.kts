rootProject.name = "vulkanic"

include("core")
include("helpers")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./libs.versions.toml"))
        }
    }
}