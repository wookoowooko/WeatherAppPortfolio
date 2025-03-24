plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "io.wookoo.geolocation"
}

dependencies {
    projects.apply {
        implementation(core.common)
        implementation(core.domain)
    }
    implementation(libs.play.services.location)
}