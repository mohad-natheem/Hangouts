package cloud.pensive.hangouts.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

@Composable
fun CommonOutlinedTextField(
    modifier: Modifier = Modifier,
    inputValue: String?,
    onValueChange: (String) -> Unit,
    supportingText: String = "Supporting Text",
    placeHolder: String = "Placeholder text",
    @DrawableRes trailingIcon: Int,
    @DrawableRes leadingIcon: Int,

    ) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = inputValue ?: "",
        onValueChange = onValueChange,
        singleLine = true,
        readOnly = false,
        supportingText = { Text(supportingText) },
        placeholder = {
            Text(
                text = placeHolder,
                style = MaterialTheme.typography.labelMedium
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.primary
        ),
        shape = CircleShape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedLabelColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
        ),
        trailingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(trailingIcon),
                contentDescription = "Trailing Icon",
                tint = Color.White
            )
        },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(leadingIcon),
                contentDescription = "Search Map",
                tint = Color.White
            )
        }
    )
}