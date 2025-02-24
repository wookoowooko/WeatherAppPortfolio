plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.android.library.compose)
    alias(libs.plugins.weather.app.detekt)
}

android {
    namespace = "io.wookoo.design.system"
}

dependencies {

    libs.apply {
        api(material.icons.extended)
        implementation(androidx.ui.tooling.preview)
        implementation(androidx.ui.tooling)
        implementation(androidx.material3)
        api(androidx.ui.text.google.fonts)
    }
}
