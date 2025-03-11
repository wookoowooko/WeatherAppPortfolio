plugins {
    alias(libs.plugins.weather.app.hilt)
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.detekt)
}

android {
    namespace = "io.wookoo.connectivity.observer"
}

dependencies {
    projects.apply {
        implementation(core.domain)
    }
}