package io.wookoo.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {

        buildFeatures {
            compose = true
        }

        with(pluginManager) {
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("implementation", libs.findLibrary("androidx-ui-tooling-preview").get())
            add("debugImplementation", libs.findLibrary("androidx-ui-tooling").get())
            add("implementation", libs.findLibrary("androidx-material3").get())
            add("implementation", libs.findLibrary("navigation-compose").get())
        }
    }

}