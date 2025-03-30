plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.android.library.compose)
    alias(libs.plugins.weather.app.detekt)
}

android {
    namespace = "io.wookoo.design.system"
}

dependencies {
    projects.core.apply {
        api(androidresources)
    }

    libs.apply {
        api(material.icons.extended)
        api(kotlinx.datetime)
        implementation(androidx.ui.tooling.preview)
        implementation(androidx.ui.tooling)
        implementation(bundles.material)
        implementation(lottie.compose)
        api(androidx.ui.text.google.fonts)
        api(androidx.core.splashscreen)
    }
}
