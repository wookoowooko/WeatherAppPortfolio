plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.google.protobuf.gp) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false

}
tasks.register("detektAll") {
    dependsOn(
        subprojects.flatMap { project ->
            listOf(
                "${project.path}:detektDebug",
                "${project.path}:detektDebugAndroidTest",
                "${project.path}:detektDebugUnitTest",
                "${project.path}:detektTest",
                "${project.path}:detektMain"
            ).filter { taskName ->
                project.tasks.findByPath(taskName) != null
            }
        }
    )
}