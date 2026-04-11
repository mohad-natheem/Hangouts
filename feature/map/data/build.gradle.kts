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
    
    // Koin for dependency injection
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    
    // Google Play Services for location
    implementation(libs.google.android.gms.play.services.location)
}