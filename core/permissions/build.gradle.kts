plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.android.library.compose)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "io.wookoo.permissions"
}
dependencies{
    projects.core.apply {
        implementation(androidresources)
    }
}
