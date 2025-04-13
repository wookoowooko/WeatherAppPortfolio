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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "WeatherAppPortfolio"
include(":app")

include(":core:design-system")
include(":core:domain")
include(":core:data")
include(":core:network")
include(":core:database")
include(":core:common")
include(":core:datastore")
include(":core:geolocation")
include(":core:permissions")
include(":core:models")
include(":core:widgets")
include(":core:androidresources")
include(":core:connectivity-observer")
include(":core:synchronizer")
include(":core:mappers")
include(":core:worker")

include(":features:main")
include(":features:welcome")
include(":features:weekly")
include(":features:cities")
include(":features:settings")

