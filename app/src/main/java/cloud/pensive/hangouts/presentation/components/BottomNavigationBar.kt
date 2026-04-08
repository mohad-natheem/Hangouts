package cloud.pensive.hangouts.presentation.components

import BottomNavKey
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cloud.pensive.core.presentation.utils.utils.bottomPadding
import cloud.pensive.core.presentation.utils.utils.horizontalPadding
import cloud.pensive.core.presentation.utils.utils.startPadding
import cloud.pensive.core.presentation.utils.utils.verticalPadding

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navigationScreens: List<BottomNavKey>,
    selectedScreen: BottomNavKey,
    onClick: (BottomNavKey) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier
            .bottomPadding(30.dp)
            .startPadding(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
            .horizontalPadding(28.dp)
            .verticalPadding(18.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            navigationScreens.forEach { navScreen ->
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { onClick(navScreen) },
                    imageVector = ImageVector.vectorResource(navScreen.icon),
                    contentDescription = navScreen.label,
                    tint = if (navScreen == selectedScreen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

