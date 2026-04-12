plugins {
    alias(libs.plugins.hangouts.android.library.compose)
}

android {
    namespace = "cloud.pensive.core.presentation"
}

dependencies {
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.ui.graphics)

    // Required for Compose preview rendering in Android Studio
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(projects.core.domain)
}