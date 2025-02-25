plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.kotlinxSerialization)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "io.wookoo.network"
}

dependencies {
    api(libs.bundles.network)
    implementation(projects.core.domain)
}