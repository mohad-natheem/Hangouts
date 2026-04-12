package cloud.pensive.core.presentation.commonComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Creates a glass morphism effect modifier that can be applied to any composable.
 * This effect creates a frosted glass appearance with semi-transparent background and border.
 * Content inside remains sharp and unblurred.
 *
 * @param alpha Transparency level of the glass (0f = fully transparent, 1f = fully opaque)
 * @param blurRadius Radius parameter (affects border visibility)
 * @param backgroundColor The base color of the glass effect
 * @param shape The shape of the glass container
 * @param borderColor Color of the glass edge border
 * @param borderWidth Width of the glass edge border
 */
@Composable
fun Modifier.glassEffect(
    alpha: Float = 0.7f,
    blurRadius: Float = 8f,
    backgroundColor: Color = Color.White,
    shape: Shape = RectangleShape,
    borderColor: Color = MaterialTheme.colorScheme.onSurface,
    borderWidth: Float = 1f
): Modifier {
    return this
        .clip(shape)
        .background(backgroundColor.copy(alpha = alpha))
        .border(
            width = borderWidth.dp,
            color = borderColor.copy(alpha = 0.3f),
            shape = shape
        )
}

/**
 * A pre-built Glass Effect Box composable that can be used to wrap content.
 *
 * @param modifier Additional modifiers to apply
 * @param alpha Transparency level of the glass (0f = fully transparent, 1f = fully opaque)
 * @param blurRadius The radius of the blur effect in dp
 * @param backgroundColor The base color of the glass effect
 * @param content The composable content to display inside the glass effect box
 */
@Composable
fun GlassEffectBox(
    modifier: Modifier = Modifier,
    alpha: Float = 0.3f,
    blurRadius: Float = 8f,
    backgroundColor: Color = Color.White,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.glassEffect(
            alpha = alpha,
            blurRadius = blurRadius,
            backgroundColor = backgroundColor
        )
    ) {
        content()
    }
}

/**
 * A glass effect variant optimized for dark themes.
 * Uses a darker, semi-transparent color suitable for dark mode UIs.
 *
 * @param alpha Transparency level of the glass (0f = fully transparent, 1f = fully opaque)
 * @param blurRadius The radius of the blur effect in dp
 * @param shape The shape of the glass container
 */
@Composable
fun Modifier.darkGlassEffect(
    alpha: Float = 0.25f,
    blurRadius: Float = 8f,
    shape: Shape = RectangleShape
): Modifier {
    return this.glassEffect(
        alpha = alpha,
        blurRadius = blurRadius,
        backgroundColor = Color(0xFF2A2A2A),
        borderColor = Color.White,
        shape = shape
    )
}

/**
 * A glass effect variant optimized for light themes.
 * Uses a lighter, semi-transparent color suitable for light mode UIs.
 *
 * @param alpha Transparency level of the glass (0f = fully transparent, 1f = fully opaque)
 * @param blurRadius The radius of the blur effect in dp
 * @param shape The shape of the glass container
 */
@Composable
fun Modifier.lightGlassEffect(
    alpha: Float = 0.25f,
    blurRadius: Float = 8f,
    shape: Shape = RectangleShape
): Modifier {
    return this.glassEffect(
        alpha = alpha,
        blurRadius = blurRadius,
        backgroundColor = Color(0xFFF0F0F0),
        borderColor = Color.White,
        shape = shape
    )
}

/**
 * A pre-built Dark Glass Effect Box composable.
 *
 * @param modifier Additional modifiers to apply
 * @param alpha Transparency level of the glass
 * @param blurRadius The radius of the blur effect in dp
 * @param content The composable content to display
 */
@Composable
fun DarkGlassEffectBox(
    modifier: Modifier = Modifier,
    alpha: Float = 0.25f,
    blurRadius: Float = 8f,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.darkGlassEffect(
            alpha = alpha,
            blurRadius = blurRadius
        )
    ) {
        content()
    }
}

/**
 * A pre-built Light Glass Effect Box composable.
 *
 * @param modifier Additional modifiers to apply
 * @param alpha Transparency level of the glass
 * @param blurRadius The radius of the blur effect in dp
 * @param content The composable content to display
 */
@Composable
fun LightGlassEffectBox(
    modifier: Modifier = Modifier,
    alpha: Float = 0.25f,
    blurRadius: Float = 8f,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.lightGlassEffect(
            alpha = alpha,
            blurRadius = blurRadius
        )
    ) {
        content()
    }
}





