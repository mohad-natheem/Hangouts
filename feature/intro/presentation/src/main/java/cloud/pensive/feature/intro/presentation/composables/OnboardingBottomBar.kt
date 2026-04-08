package cloud.pensive.feature.intro.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cloud.pensive.hangouts.presentation.utils.bottomPadding
import cloud.pensive.hangouts.presentation.utils.horizontalPadding

@Composable
fun OnboardingBottomBar(
    modifier: Modifier = Modifier,
    currentPage: Int,
    pageCount: Int,
    isLastPage: Boolean,
    onNextClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .horizontalPadding(12.dp)
            .bottomPadding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(pageCount) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (currentPage == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                    )
                }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = if (isLastPage) "Get Started" else "Next",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

    }

}