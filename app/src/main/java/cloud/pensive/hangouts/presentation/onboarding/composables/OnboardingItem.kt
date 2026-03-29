package cloud.pensive.hangouts.presentation.onboarding.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cloud.pensive.hangouts.domain.model.OnboardingModel
import cloud.pensive.hangouts.presentation.utils.horizontalPadding
import kotlinx.coroutines.delay

@Composable
fun OnboardingItem(
    modifier: Modifier = Modifier,
    page: OnboardingModel,
    isVisible: Boolean

) {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            startAnimation = false
            delay(500)
            startAnimation = true
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 1000))
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier.size(80.dp),
                imageVector = ImageVector.vectorResource(page.imageRes),
                contentDescription = page.title,
                tint = MaterialTheme.colorScheme.primary

            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = page.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }

}