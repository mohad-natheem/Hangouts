plugins {
    alias(libs.plugins.hangouts.android.feature.ui)

}

android {
    namespace = "cloud.pensive.auth.presentation"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}