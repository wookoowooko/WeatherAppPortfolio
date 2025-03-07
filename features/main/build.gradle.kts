plugins {
    alias(libs.plugins.weather.app.feature)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "io.wookoo.main"
}
dependencies{

    projects.apply {
        implementation(core.data)
        implementation(core.geolocation)
        implementation(core.androidresources)
    }
}
