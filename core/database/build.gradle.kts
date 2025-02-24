plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.room)
    alias(libs.plugins.weather.app.hilt)
    alias(libs.plugins.weather.app.detekt)
}

android {
    namespace = "io.wookoo.database"
}

dependencies {
    api(projects.core.domain)
}