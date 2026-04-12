package cloud.pensive.core.presentation.commonComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cloud.pensive.core.presentation.utils.utils.WindowInsetsHelper.cameraInsetPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CommonDropDown(
    modifier: Modifier = Modifier,
    dropDownMenuItems: List<T>,
    onDropDownMenuItemSelected: (T) -> Unit,
    itemToString: (T) -> String,
    isDropDownExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    textFieldData: TextFieldData,

    ) {
    ExposedDropdownMenuBox(
        expanded = isDropDownExpanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
            .cameraInsetPadding(includeTopPadding = true)
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        HangoutsTextField(
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
            state = textFieldData.textFieldValue,
            startIcon = textFieldData.leadingIcon,
            endIcon = textFieldData.trailingIcon,
            hint = textFieldData.textFieldPlaceHolder,
            title = null
        )

        CompositionLocalProvider(
            LocalTonalElevationEnabled provides false
        ) {

            ExposedDropdownMenu(
                modifier = Modifier
                    .offset(y = 8.dp)
                    .glassEffect(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    ),
                expanded = isDropDownExpanded,
                onDismissRequest = {
                    onExpandedChange(false)
                },
                containerColor = Color.Transparent,
                shape = RoundedCornerShape(12.dp),
            ) {
                dropDownMenuItems.forEach { item ->
                    DropdownMenuItem(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        text = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(itemToString(item))
                            }
                        },
                        onClick = {
                            onDropDownMenuItemSelected(item)
                        }
                    )
                }
            }
        }
    }
}


data class TextFieldData(
    val textFieldValue: TextFieldState,
    val trailingIcon: ImageVector?,
    val leadingIcon: ImageVector?,
    val textFieldPlaceHolder: String
)