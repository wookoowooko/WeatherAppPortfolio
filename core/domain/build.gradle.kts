plugins {
    alias(libs.plugins.weather.app.jvmLibrary)
    alias(libs.plugins.weather.app.detekt)
}

dependencies{
    libs.apply {
        api(libs.javax.inject)
        implementation(kotlinx.datetime)
        testImplementation(bundles.jvm.test)
    }
    projects.core.apply {
        api(models)
    }
    testImplementation(kotlin("test"))
}
