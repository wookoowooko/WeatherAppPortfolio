plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "io.wookoo.data"
}

dependencies {
    api(projects.core.database)
    api(projects.core.domain)
    api(projects.core.network)
    api(projects.core.datastore)
}