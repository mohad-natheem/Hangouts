pluginManagement {
    includeBuild("build-logic")
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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Hangouts"
include(":app")
include(":feature:intro:data")
include(":feature:intro:domain")
include(":feature:intro:presentation")
include(":feature:map:data")
include(":feature:map:presentation")
include(":feature:map:domain")
include(":auth:data")
include(":auth:presentation")
include(":auth:domain")
include(":core:data")
include(":core:database")
include(":core:presentation")
include(":core:domain")
