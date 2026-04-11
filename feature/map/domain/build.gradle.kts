plugins {
    alias(libs.plugins.hangouts.jvm.library)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(projects.core.domain)
}