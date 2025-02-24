plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
    }
}

dependencies {
    compileOnly(libs.android.plugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}
tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kotlinDetekt") {
            id = "weather.app.detekt"
            implementationClass = "DetektConventionPlugin"
        }
        register("androidApplication") {
            id = "weather.app.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "weather.app.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "weather.app.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "weather.app.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("feature") {
            id = "weather.app.feature"
            implementationClass = "FeatureConventionPlugin"
        }
        register("kotlinxSerialization") {
            id = "weather.app.kotlinxSerialization"
            implementationClass = "KotlinxSerializationConventionPlugin"
        }
        register("jvmLibrary") {
            id = "weather.app.jvmLibrary"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("hilt") {
            id = "weather.app.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("room") {
            id = "weather.app.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}
