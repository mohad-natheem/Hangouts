package cloud.pensive.core.presentation.utils.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

fun Modifier.horizontalPadding(padding: Dp): Modifier {
    return this.then(Modifier.padding(horizontal = padding))
}

fun Modifier.verticalPadding(padding: Dp): Modifier {
    return this.then(Modifier.padding(vertical = padding))
}

fun Modifier.bottomPadding(padding: Dp): Modifier {
    return this.then(Modifier.padding(bottom = padding))
}

fun Modifier.topPadding(padding: Dp): Modifier {
    return this.then(Modifier.padding(top = padding))
}

fun Modifier.startPadding(padding: Dp): Modifier {
    return this.then(Modifier.padding(start = padding))
}

fun Modifier.endPadding(padding: Dp): Modifier {
    return this.then(Modifier.padding(end = padding))
}