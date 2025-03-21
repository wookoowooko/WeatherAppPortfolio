plugins {
    alias(libs.plugins.weather.app.hilt)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.android.library)
}

android {
    namespace = "io.wookoo.worker"
}

dependencies {
    ksp(libs.hilt.ext.compiler)
    implementation(libs.hilt.ext.work)
    implementation(libs.androidx.work.ktx)
    projects.core.apply {
        implementation(data)
    }
}