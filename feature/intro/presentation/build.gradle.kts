plugins {
    alias(libs.plugins.hangouts.android.feature.ui)

}

android {
    namespace = "cloud.pensive.feature.intro.presentation"
}

dependencies {
    implementation(projects.core.presentation)
    implementation(projects.feature.intro.domain)
}