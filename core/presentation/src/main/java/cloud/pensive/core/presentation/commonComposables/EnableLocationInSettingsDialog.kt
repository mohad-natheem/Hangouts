package cloud.pensive.core.presentation.commonComposables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cloud.pensive.core.presentation.R
import cloud.pensive.core.presentation.theme.HangoutsTheme
import cloud.pensive.core.presentation.utils.utils.horizontalPadding
import cloud.pensive.core.presentation.utils.utils.topPadding
import cloud.pensive.core.presentation.utils.utils.verticalPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnableLocationInSettingsDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onQuitClick: () -> Unit,
    onOpenSettingsClick: () -> Unit
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier.glassEffect(
                alpha = 0.7f,
                backgroundColor = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium,
                borderColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = MaterialTheme.shapes.medium,
            color = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(24.dp)
                    .verticalPadding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_error),
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = "Location Permission"

                )
                Text(
                    modifier = Modifier.topPadding(12.dp),
                    text = "You have to enable location in settings to continue.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                HangoutsActionButton(
                    modifier = Modifier.topPadding(16.dp),
                    text = "Open Settings",
                    onClick = onOpenSettingsClick,
                    isLoading = false
                )
                HangoutsOutlinedActionButton(
                    modifier = Modifier.topPadding(12.dp),
                    text = "Quit",
                    onClick = onQuitClick,
                    isLoading = false
                )
            }
        }
    }
}


@Preview
@Composable
private fun EnableLocationInSettingsDialogPreview() {
    HangoutsTheme {
    EnableLocationInSettingsDialog(
        onDismissRequest = {},
        onQuitClick = {},
        onOpenSettingsClick = {}
    )
    }

}
