plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "io.wookoo.data"
}

//api dependencies for app module for hilt di correctly
dependencies {
    api(projects.core.database)
    api(projects.core.domain)
    api(projects.core.network)
    api(projects.core.datastore)
    api(projects.core.geolocation)
    api(projects.core.mappers)
    api(projects.core.synchronizer)
    api(projects.core.common)
}