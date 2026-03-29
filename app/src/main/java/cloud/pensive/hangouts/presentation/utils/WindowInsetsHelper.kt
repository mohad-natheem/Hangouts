package cloud.pensive.hangouts.presentation.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * WindowInsetsHelper - Smart orientation-aware padding for system insets
 * Automatically handles navigation bar padding based on device orientation
 */
object WindowInsetsHelper {

    @Composable
    fun Modifier.navigationBarPadding(): Modifier {
        val configuration = LocalConfiguration.current
        val navigationBarPadding = WindowInsets.navigationBars.asPaddingValues()
        val displayCutoutPadding = WindowInsets.displayCutout.asPaddingValues()

        // Determine if device is in landscape or portrait

        return this.then(
            Modifier.padding(
                start = displayCutoutPadding.calculateLeftPadding(LayoutDirection.Ltr),
                end = displayCutoutPadding.calculateRightPadding(LayoutDirection.Ltr),
                bottom = navigationBarPadding.calculateBottomPadding()
            )
        )
    }

    @Composable
    fun Modifier.cameraInsetPadding(
        includeTopPadding: Boolean = false,
    ): Modifier {
        val displayCutoutPadding = WindowInsets.displayCutout.asPaddingValues()
        val statusBarPadding = WindowInsets.statusBars.asPaddingValues()

        val topPaddingValues = if (displayCutoutPadding.calculateTopPadding() > 0.dp) {
            displayCutoutPadding.calculateTopPadding()
        } else {
            statusBarPadding.calculateTopPadding()
        }

        // Determine if device is in landscape or portrait

        return this.then(
            Modifier.padding(
                top = if (includeTopPadding) {
                    topPaddingValues
                } else 0.dp,
                start = displayCutoutPadding.calculateLeftPadding(LayoutDirection.Ltr),
                end = displayCutoutPadding.calculateRightPadding(LayoutDirection.Ltr),
            )
        )
    }


}


