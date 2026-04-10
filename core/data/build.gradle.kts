plugins {
    alias(libs.plugins.hangouts.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "cloud.pensive.core.data"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.bundles.koin)
    
    //DataStore
    implementation(libs.androidx.datastore.preferences)


    implementation(projects.core.domain)
    implementation(projects.core.database)
}