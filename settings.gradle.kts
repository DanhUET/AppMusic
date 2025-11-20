pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "App Music"
include(":app")
include(":core-ui")
include(":feature-home")
include(":feature-library")
include(":feature-discovery")
include(":feature-album")
include(":feature-recommended")
include(":feature-player")
include(":feature-recent-heard")
include(":core-database")
include(":core-network")
include(":core-navigation")
include(":feature-favorite-songs")
include(":feature-playlist")
