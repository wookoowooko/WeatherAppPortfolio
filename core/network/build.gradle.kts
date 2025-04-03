import java.util.Properties

plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.kotlinxSerialization)
    alias(libs.plugins.weather.app.hilt)
}

val secretsFile = rootProject.file("secrets.properties")
val secrets = Properties().apply {
    if (secretsFile.exists()) {
        load(secretsFile.inputStream())
    }
}

android {
    namespace = "io.wookoo.network"

    buildTypes {
        debug {
            buildConfigField("String", "REVERSE_GEOCODING_API_KEY", "\"${secrets["REVERSE_GEOCODING_API_KEY"]}\"")
        }
        release {
            buildConfigField("String", "REVERSE_GEOCODING_API_KEY", "\"${secrets["REVERSE_GEOCODING_API_KEY"]}\"")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    api(libs.bundles.network)
    implementation(projects.core.domain)

    testImplementation(libs.bundles.jvm.test)
    testImplementation(libs.mockwebserver)
}