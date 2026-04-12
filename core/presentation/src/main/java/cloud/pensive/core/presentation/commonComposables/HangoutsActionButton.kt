package cloud.pensive.core.presentation.commonComposables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cloud.pensive.core.presentation.theme.HangoutsTheme



@Composable
fun HangoutsActionButton(
    modifier: Modifier = Modifier,
    text: String,
    variant: HangoutsActionButtonVariant = HangoutsActionButtonVariant.PRIMARY,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(100f),
        modifier = modifier
            .height(IntrinsicSize.Min)
            .then(
                when (variant) {
                    HangoutsActionButtonVariant.PRIMARY -> {
                        Modifier.background(
                            brush = appGradientBrush,
                            shape = RoundedCornerShape(24.dp)
                        )
                    }

                    HangoutsActionButtonVariant.SECONDARY -> {
                        Modifier.background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(24.dp)
                        )
                    }


                }
            )

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                fontWeight = FontWeight.Medium,
                color = when (variant) {
                    HangoutsActionButtonVariant.PRIMARY -> MaterialTheme.colorScheme.onPrimary
                    HangoutsActionButtonVariant.SECONDARY -> MaterialTheme.colorScheme.onSecondary
                }
            )
        }
    }
}

@Composable
fun HangoutsOutlinedActionButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(100f),
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

enum class HangoutsActionButtonVariant {
    PRIMARY,
    SECONDARY
}

@Preview
@Composable
private fun HangoutsActionButtonPreview() {
    HangoutsTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HangoutsActionButton(
                text = "Action Button",
                isLoading = false,
                onClick = {}
            )

            HangoutsActionButton(
                text = "Action Button",
                variant = HangoutsActionButtonVariant.SECONDARY,
                isLoading = false,
                onClick = {}
            )

        }
    }
}


@Preview
@Composable
private fun HangoutsOutinedActionButtonPreview() {
    HangoutsTheme {
        HangoutsOutlinedActionButton(
            text = "Action Button",
            isLoading = false,
            onClick = {}
        )
    }
}