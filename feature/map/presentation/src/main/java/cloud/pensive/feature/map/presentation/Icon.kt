package cloud.pensive.feature.map.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

val GalleryIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.gallery)

val ArtIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.art)

val BookIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.book)

val DumbellIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.exercise)

val BurgerIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.food)

val OtherIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.other)