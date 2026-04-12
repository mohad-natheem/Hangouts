package cloud.pensive.feature.map.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cloud.pensive.core.presentation.ArrowRightIcon
import cloud.pensive.core.presentation.KeyboardRightArrowIcon
import cloud.pensive.core.presentation.commonComposables.glassEffect
import cloud.pensive.core.presentation.utils.utils.horizontalPadding
import cloud.pensive.core.presentation.utils.utils.verticalPadding

@Composable
fun BookingOverview(modifier: Modifier = Modifier, items: List<BookingsOverViewData>) {
    val scrollState = rememberScrollState()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items.map { item ->
            BookingOverviewItem(bookingOverviewItem = item)
        }
    }

}

@Composable
fun BookingOverviewItem(modifier: Modifier = Modifier, bookingOverviewItem: BookingsOverViewData) {
    Surface(
        modifier = modifier.glassEffect(
            backgroundColor = MaterialTheme.colorScheme.surface,
            shape = CircleShape
        ),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .horizontalPadding(20.dp)
                .verticalPadding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = bookingOverviewItem.icon,
                contentDescription = null
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = bookingOverviewItem.title,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = bookingOverviewItem.peopleJoined,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = KeyboardRightArrowIcon,
                    contentDescription = stringResource(R.string.go_to_booking)
                )
            }

        }
    }

}

data class BookingsOverViewData(
    val title: String,
    val peopleJoined: String,
    val icon: ImageVector,
)