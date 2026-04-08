package cloud.pensive.hangouts.presentation.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import cloud.pensive.hangouts.R
import cloud.pensive.core.presentation.utils.utils.horizontalPadding
import cloud.pensive.core.presentation.utils.utils.topPadding
import cloud.pensive.core.presentation.utils.utils.verticalPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnableLocationDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onQuitClick: () -> Unit,
    onRequestPermissionClick: () -> Unit
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier,
            color = Color.Black,
            shape = MaterialTheme.shapes.medium,
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
                    text = "Please provide location permission to continue",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .topPadding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    onClick = onQuitClick
                ) {
                    Text(
                        text = "Quit"
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .topPadding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    onClick = onRequestPermissionClick
                ) {
                    Text(
                        text = "Request Permission"
                    )
                }
            }
        }
    }

}