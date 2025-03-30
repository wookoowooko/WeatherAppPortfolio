plugins {
    alias(libs.plugins.weather.app.feature)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "io.wookoo.settings"
}

dependencies {
    implementation(projects.core.data)
}