plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.android.library.compose)
}

android {
    namespace = "io.wookoo.common"
}

dependencies {
    libs.apply {
        implementation(kotlinx.datetime)
    }
    projects.core.apply {
        implementation(domain)
        api(designSystem)
    }
}