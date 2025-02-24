import io.wookoo.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinxSerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            applyDependencies()
        }
    }

    private fun Project.applyPlugins() {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.plugin.serialization")
        }
    }

    private fun Project.applyDependencies() {
        dependencies {
            "implementation"(libs.findLibrary("kotlinx-serialization").get())
        }
    }
}