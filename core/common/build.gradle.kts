plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.detekt)
}

android {
    namespace = "io.wookoo.common"
}

dependencies {
    projects.core.apply {
        implementation(domain)
        api(designSystem)
    }
}