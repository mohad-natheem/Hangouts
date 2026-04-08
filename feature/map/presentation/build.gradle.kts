plugins {
    alias(libs.plugins.hangouts.android.feature.ui)

}

android {
    namespace = "cloud.pensive.feature.map.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.feature.map.domain)
    implementation(projects.core.presentation)
}