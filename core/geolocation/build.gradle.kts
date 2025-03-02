plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "io.wookoo.geolocation"
}

dependencies {
    implementation(project(":core:common"))

}