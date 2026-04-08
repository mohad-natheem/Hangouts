plugins {
    alias(libs.plugins.hangouts.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "cloud.pensive.auth.data"

}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}