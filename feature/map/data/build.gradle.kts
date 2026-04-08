plugins {
    alias(libs.plugins.hangouts.android.library)
}

android {
    namespace = "cloud.pensive.feature.map.data"

}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.feature.map.domain)
}