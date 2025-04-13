plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.android.library.compose)
    alias(libs.plugins.weather.app.hilt)
    alias(libs.plugins.weather.app.detekt)
}

android {
    namespace = "io.wookoo.widgets"
}

dependencies {
    api(libs.bundles.glance)
    projects.core.apply {
        implementation(androidresources)
        implementation(data)
    }
}