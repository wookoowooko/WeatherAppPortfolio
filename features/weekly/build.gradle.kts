plugins {
    alias(libs.plugins.weather.app.feature)
    alias(libs.plugins.weather.app.hilt)
    alias(libs.plugins.weather.app.detekt)
}

android {
    namespace = "io.wookoo.weekly"
}

dependencies {

    projects.apply {
        implementation(core.common)
        implementation(core.data)
    }

    libs.apply {
        implementation(bundles.material)
        implementation(androidx.fragment.ktx)
        implementation(androidx.fragment.compose)
        implementation(androidx.constraintlayout)
        implementation(view.binding.delegate)
        implementation(adapter.core)
        implementation(adapter.viewbinding)
        implementation(lottie.xml)
    }

}