package cloud.pensive.core.presentation.commonComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

// Linear gradient for larger composables
val appGradientBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFF2994A), // orange
        Color(0xFFEB5757), // pink/red
        Color(0xFFC0392B)
    ), start = Offset(0f, 0f), end = Offset(1000f, 1000f)
)

// Radial gradient for circular buttons/FABs - shows all 3 colors
val appRadialGradientBrush = Brush.radialGradient(
    colors = listOf(
        Color(0xFFF2994A), // orange (center)
        Color(0xFFEB5757), // pink/red (middle)
        Color(0xFFC0392B)  // dark red (edges)
    ),
    radius = 500f // Large radius to show all colors
)

// Compact linear gradient for small composables
val appCompactGradientBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFF2994A), // orange
        Color(0xFFEB5757), // pink/red
        Color(0xFFC0392B)
    ), start = Offset(0f, 0f), end = Offset(100f, 100f)
)

/**
 * Applies a gradient background to any composable.
 * The gradient goes from orange -> pink/red -> dark red.
 *
 * @param shape The shape to apply the gradient with (default: 24.dp rounded corners)
 * @param brush Custom gradient brush (default: appGradientBrush)
 */
fun Modifier.applyGradientBackground(
    shape: Shape = RoundedCornerShape(24.dp),
    brush: Brush = appGradientBrush
): Modifier {
    return this.background(
        brush = brush,
        shape = shape
    )
}

/**
 * Applies a radial gradient background - perfect for circular buttons/FABs.
 * Shows all 3 colors clearly: orange center -> pink/red middle -> dark red edges.
 *
 * @param shape The shape to apply the gradient with (default: CircleShape)
 * @param brush Custom gradient brush (default: appRadialGradientBrush)
 */
fun Modifier.applyRadialGradientBackground(
    shape: Shape,
    brush: Brush = appRadialGradientBrush
): Modifier {
    return this.background(
        brush = brush,
        shape = shape
    )
}

/**
 * Applies a compact linear gradient - suitable for small composables where
 * the standard gradient offsets are too large.
 *
 * @param shape The shape to apply the gradient with
 * @param brush Custom gradient brush (default: appCompactGradientBrush)
 */
fun Modifier.applyCompactGradientBackground(
    shape: Shape = RoundedCornerShape(24.dp),
    brush: Brush = appCompactGradientBrush
): Modifier {
    return this.background(
        brush = brush,
        shape = shape
    )
}

