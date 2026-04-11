package cloud.pensive.hangouts.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import cloud.pensive.feature.map.presentation.DiscoverScreenRoot
import cloud.pensive.hangouts.main.DetailsScreen
import cloud.pensive.hangouts.main.ProfileScreen
import cloud.pensive.hangouts.main.SettingsScreen

@Composable
fun CustomNavDisplay(
    modifier: Modifier = Modifier,
    currentBackStack: List<NavKey>,
    addToBackStack: (NavKey) -> Unit,
    onHandleBackPressed: () -> Unit
) {
    NavDisplay(
        modifier = modifier.fillMaxSize(),
        backStack = currentBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<BottomNavKey.Discover> {
                DiscoverScreenRoot()
            }
            entry<BottomNavKey.Search> {
                ProfileScreen(
                    onNavigateToDetails = {
                        addToBackStack(it)
                    })
            }
            entry<BottomNavKey.Bookings> {
                SettingsScreen(
                    onNavigateToDetails = {
                        addToBackStack(it)
                    })
            }

            entry<MainNavKey.Details> {
                DetailsScreen(
                    key = it,
                    onBackPressed = onHandleBackPressed
                )
            }
        })
}