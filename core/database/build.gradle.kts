plugins {
    alias(libs.plugins.hangouts.android.library)
    alias(libs.plugins.hangouts.android.room)
}

android {
    namespace = "cloud.pensive.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
}