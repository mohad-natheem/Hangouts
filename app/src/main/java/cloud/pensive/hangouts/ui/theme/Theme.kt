package cloud.pensive.hangouts.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


val DarkColorScheme = darkColorScheme(

    primary = Color(0xFFE8EAED), // soft white
    onPrimary = Color(0xFF1C1C1C),
    primaryContainer = Color(0xFF2A2A2A),
    onPrimaryContainer = Color(0xFFF5F5F5),

    secondary = Color(0xFFB0B3B8),
    onSecondary = Color(0xFF1C1C1C),
    secondaryContainer = Color(0xFF2F3133),
    onSecondaryContainer = Color(0xFFE4E6EB),

    tertiary = Color(0xFFD0D0D0),
    onTertiary = Color(0xFF1A1A1A),
    tertiaryContainer = Color(0xFF333333),
    onTertiaryContainer = Color(0xFFF1F1F1),

    background = Color(0xFF18191A),   // body background
    onBackground = Color(0xFFE4E6EB),

    surface = Color(0xFF242526),      // cards / messages
    onSurface = Color(0xFFE4E6EB),

    surfaceVariant = Color(0xFF3A3B3C), // inputs / chips
    onSurfaceVariant = Color(0xFFB0B3B8),

    outline = Color(0xFF4E4F50),
    outlineVariant = Color(0xFF3A3B3C),

    error = Color(0xFFFF6B6B),
    onError = Color(0xFF2B0000),
    errorContainer = Color(0xFF8C1D1D),
    onErrorContainer = Color(0xFFFFDAD6),

    scrim = Color(0xFF000000),

    inverseSurface = Color(0xFFE4E6EB),
    inverseOnSurface = Color(0xFF1C1C1C),
    inversePrimary = Color(0xFFDADCE0)
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun HangoutsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}