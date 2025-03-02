plugins {
    alias(libs.plugins.weather.app.feature)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "io.wookoo.main"
}
dependencies{
    implementation(project(":core:geolocation"))
    projects.apply {
        implementation(core.data)
    }
}
