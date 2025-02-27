import io.wookoo.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            applyDependencies()
        }
    }

    private fun Project.applyPlugins() {
        pluginManager.apply {
            apply("weather.app.android.library")
            apply("weather.app.android.library.compose")
            apply("org.jetbrains.kotlin.plugin.serialization")
        }
    }

    private fun Project.applyDependencies() {
        dependencies {
            add("implementation", libs.findBundle("material").get())
            add("implementation", libs.findLibrary("navigation-compose").get())
            add("implementation", libs.findLibrary("kotlinx-serialization").get())
            add("implementation", libs.findLibrary("hilt-nav-compose").get())
            add("api", project(":core:common"))
        }
    }
}
