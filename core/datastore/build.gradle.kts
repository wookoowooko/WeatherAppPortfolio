import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.weather.app.detekt)
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.hilt)
    alias(libs.plugins.google.protobuf.gp)
}

android {
    namespace = "io.wookoo.datastore"
}
protobuf {
    protoc {
        artifact = libs.google.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
                id("kotlin") {
                    option("lite")
                }
            }
        }
    }
}


dependencies {
    libs.apply {
        implementation(bundles.datastore)
        androidTestImplementation(androidx.datastore.core)
        androidTestImplementation(bundles.android.test)
    }

    projects.apply {
        implementation(core.domain)
    }


}