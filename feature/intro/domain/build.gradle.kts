plugins {
    alias(libs.plugins.hangouts.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}