import com.android.build.api.dsl.ApplicationExtension
import io.wookoo.convention.configureKotlinAndroid
import io.wookoo.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            applyDependencies()
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
            }
        }
    }

    private fun Project.applyPlugins() {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.plugin.serialization")
        }
    }

    private fun Project.applyDependencies() {
        dependencies {
            add("implementation", libs.findLibrary("kotlinx-coroutines-core").get())
            add("implementation", libs.findLibrary("kotlinx-serialization").get())
        }
    }
}
