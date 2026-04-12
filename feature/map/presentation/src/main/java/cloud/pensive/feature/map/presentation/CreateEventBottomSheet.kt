package cloud.pensive.feature.map.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cloud.pensive.core.presentation.KeyboardRightArrowIcon
import cloud.pensive.core.presentation.LocationIcon
import cloud.pensive.core.presentation.commonComposables.HangoutsTextArea
import cloud.pensive.core.presentation.commonComposables.HangoutsTextField
import cloud.pensive.core.presentation.commonComposables.applyMediumGradientBackground
import cloud.pensive.core.presentation.theme.HangoutsTheme
import cloud.pensive.core.presentation.utils.utils.horizontalPadding
import cloud.pensive.core.presentation.utils.utils.topPadding
import cloud.pensive.core.presentation.utils.utils.verticalPadding
import cloud.pensive.feature.map.presentation.model.CreateEventState
import cloud.pensive.feature.map.presentation.model.EventCategory
import cloud.pensive.feature.map.presentation.model.EventType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventBottomSheet(
    modifier: Modifier = Modifier,
    createEventState: CreateEventState,
    onDismissRequest: () -> Unit,
) {
    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()


    ModalBottomSheet(
        modifier = modifier,
        sheetState = modalSheetState,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = true,
            shouldDismissOnClickOutside = true,
        ),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        onDismissRequest = {
            scope.launch {
                modalSheetState.hide()
            }.invokeOnCompletion {
                if (!modalSheetState.isVisible) {
                    onDismissRequest()
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .horizontalPadding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxHeight(0.9f),
            horizontalAlignment = Alignment.Start,

            ) {

            Text(
                text = "Create Event",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                modifier = Modifier.topPadding(8.dp),
                text = "Be an event organiser and create your own event and let others join the fun.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Text(
                modifier = Modifier.topPadding(16.dp),
                text = "Cover Photo",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Box(
                modifier = Modifier
                    .topPadding(8.dp)
                    .fillMaxWidth()
                    .applyMediumGradientBackground(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalPadding(46.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = GalleryIcon,
                        contentDescription = "Gallery Icon",
                    )
                    Text(
                        modifier = Modifier.topPadding(8.dp),
                        text = "Add a cover image",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Text(
                        modifier = Modifier.topPadding(4.dp),
                        text = "Visual only for you",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                }

            }

            EventTypeSwitchComposable(
                modifier = Modifier.topPadding(16.dp),
                eventTypes = EventType.entries,
                selectedEvent = createEventState.selectedEventType,
                onEventSelected = {}
            )

            Text(
                modifier = Modifier.topPadding(16.dp),
                text = "Event Name",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            HangoutsTextField(
                modifier = Modifier.topPadding(8.dp),
                state = createEventState.eventNameTextFieldState,
                hint = "Enter your event name"
            )

            Text(
                modifier = Modifier.topPadding(16.dp),
                text = "Event Description",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            HangoutsTextArea(
                modifier = Modifier.topPadding(8.dp),
                state = createEventState.eventDescriptionTextFieldState,
                hint = "Enter your event description"
            )

            Text(
                modifier = Modifier.topPadding(16.dp),
                text = "Max Members",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            HangoutsTextField(
                modifier = Modifier.topPadding(8.dp),
                state = createEventState.eventNameTextFieldState,
                hint = "Enter max members for this event"
            )

            Text(
                modifier = Modifier.topPadding(16.dp),
                text = "Location",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            HangoutsTextField(
                modifier = Modifier.topPadding(8.dp),
                state = createEventState.eventNameTextFieldState,
                startIcon = LocationIcon,
                hint = "Select Location on Map",
                endIcon = KeyboardRightArrowIcon
            )

            Text(
                modifier = Modifier.topPadding(16.dp),
                text = "Date",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            val state = rememberDatePickerState()

            DatePicker(
                modifier = Modifier
                    .topPadding(8.dp)
                    .clip(RoundedCornerShape(20.dp)),
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                ),
                showModeToggle = false,
                title = null,
                headline = null,
                state = state
            )
            EventCategories(
                categories = EventCategory.entries,
                selectedCategory = createEventState.selectedCategory,
            )
        }
    }
}

@Composable
fun EventCategories(
    modifier: Modifier = Modifier,
    categories: List<EventCategory>,
    selectedCategory: EventCategory
) {

    Text(
        modifier = Modifier.topPadding(16.dp),
        text = "Category",
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Row(
        modifier
            .topPadding(8.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        categories.map { category ->
            val icon = when (category) {
                EventCategory.ART -> ArtIcon
                EventCategory.BOOKS -> BookIcon
                EventCategory.FITNESS -> DumbellIcon
                EventCategory.FOOD -> BurgerIcon
                EventCategory.OTHER -> OtherIcon
            }

            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    .border(
                        width = 0.5.dp,
                        shape = CircleShape,
                        color = if (selectedCategory == category) MaterialTheme.colorScheme.primary else Color.Transparent
                    )
                    .horizontalPadding(16.dp)
                    .verticalPadding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier,
                    text = category.value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

    }

}

@Composable
fun EventTypeSwitchComposable(
    modifier: Modifier = Modifier,
    eventTypes: List<EventType>,
    selectedEvent: EventType,
    onEventSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.topPadding(16.dp),
            text = "Event Type",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            modifier = Modifier
                .topPadding(8.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerLow),
            verticalAlignment = Alignment.CenterVertically
        ) {
            eventTypes.map {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(CircleShape)
                        .background(if (selectedEvent == it) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent)
                        .verticalPadding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center

                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun CreateEventBottomSheetPreview() {
    HangoutsTheme {
        CreateEventBottomSheet(
            createEventState = CreateEventState(),
            onDismissRequest = {}
        )
    }
}