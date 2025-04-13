plugins {
    alias(libs.plugins.weather.app.android.application)
    alias(libs.plugins.weather.app.android.application.compose)
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "io.wookoo.weatherappportfolio"
    defaultConfig {
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}


dependencies {

    projects.apply {
        implementation(core.data)
        implementation(core.permissions)
        implementation(core.connectivityObserver)
        implementation(core.worker)
        implementation(core.widgets)
        implementation(features.main)
        implementation(features.welcome)
        implementation(features.weekly)
        implementation(features.cities)
        implementation(features.settings)
    }

    libs.apply {
        implementation(androidx.appcompat)
        implementation(bundles.material)
        implementation(hilt.android)
        implementation(hilt.ext.work)
    }
}