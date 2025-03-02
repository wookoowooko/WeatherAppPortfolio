plugins {
    alias(libs.plugins.weather.app.feature)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "io.wookoo.welcome"
}

dependencies {
    implementation(project(":core:permissions"))
    projects.apply {
        implementation(core.data)
        implementation(core.geolocation)
    }
}