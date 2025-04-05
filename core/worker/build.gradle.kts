plugins {
    alias(libs.plugins.weather.app.hilt)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.android.library)
}

android {
    namespace = "io.wookoo.worker"

    defaultConfig {
        testInstrumentationRunner = "io.wookoo.worker.WeatherAppTestRunner"
    }

}

dependencies {
    ksp(libs.hilt.ext.compiler)
    implementation(libs.hilt.ext.work)
    implementation(libs.androidx.work.ktx)
    projects.core.apply {
        implementation(data)
    }

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.work.testing)
}