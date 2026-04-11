plugins {
    alias(libs.plugins.hangouts.android.feature.ui)

}

android {
    namespace = "cloud.pensive.feature.map.presentation"
}

dependencies {
    implementation(libs.google.maps.android.compose)
    implementation(libs.play.services.maps)
    implementation(libs.google.android.gms.play.services.location)
    implementation(libs.androidx.material.icons.extended)

    implementation(projects.core.domain)
    implementation(projects.core.presentation)
    implementation(projects.feature.map.domain)
    implementation(projects.feature.map.data)
    
    // Koin for dependency injection
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
}