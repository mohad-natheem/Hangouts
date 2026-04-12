package cloud.pensive.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable


val ColorScheme = darkColorScheme(
    primary = HangoutsOrange,
    background = HangoutsBlack,
    surface = HangoutsDarkGray,
    secondary = HangoutsWhite,
    onSecondary = HangoutsBlack,
    tertiary = HangoutsWhite,
    primaryContainer = HangoutsGreen30,
    onPrimary = HangoutsWhite,
    onBackground = HangoutsWhite,
    onSurface = HangoutsWhite,
    onSurfaceVariant = HangoutsGray,
    error = HangoutsDarkRed,
    errorContainer = HangoutsDarkRed5
)


@Composable
fun HangoutsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {


    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}