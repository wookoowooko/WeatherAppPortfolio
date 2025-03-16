plugins {
   alias(libs.plugins.weather.app.hilt)
   alias(libs.plugins.weather.app.detekt)
   alias(libs.plugins.weather.app.feature)
}

android {
    namespace = "io.wookoo.cities"
}

dependencies{

    projects.apply {
        implementation(core.androidresources)
        implementation(core.data)
    }
}

