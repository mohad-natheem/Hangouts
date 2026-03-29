package cloud.pensive.hangouts.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cloud.pensive.hangouts.presentation.utils.WindowInsetsHelper.cameraInsetPadding

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
        CommonOutlinedTextField(
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
            inputValue = textFieldData.textFieldValue,
            onValueChange = textFieldData.onTextFieldValueChange,
            trailingIcon = textFieldData.trailingIcon,
            leadingIcon = textFieldData.leadingIcon,
            placeHolder = textFieldData.textFieldPlaceHolder,
            supportingText = textFieldData.supportingText
        )

        CompositionLocalProvider(
            LocalTonalElevationEnabled provides false
        ) {

            ExposedDropdownMenu(
                modifier = Modifier,
                expanded = isDropDownExpanded,
                onDismissRequest = {
                    onExpandedChange(false)
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
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
    val textFieldValue: String,
    val onTextFieldValueChange: (String) -> Unit,
    @DrawableRes val trailingIcon: Int,
    @DrawableRes val leadingIcon: Int,
    val textFieldPlaceHolder: String,
    val supportingText: String
)