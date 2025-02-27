plugins {
    alias(libs.plugins.weather.app.jvmLibrary)
    alias(libs.plugins.weather.app.detekt)
}
dependencies{
    libs.apply {
        api(libs.javax.inject)
        implementation(kotlinx.datetime)
        testImplementation(junit)
    }

}
