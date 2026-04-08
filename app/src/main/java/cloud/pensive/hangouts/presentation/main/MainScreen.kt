package cloud.pensive.hangouts.presentation.main

import BottomNavKey
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cloud.pensive.hangouts.presentation.components.BottomNavigationBar
import cloud.pensive.hangouts.presentation.navigation.CustomNavDisplay
import cloud.pensive.core.presentation.utils.utils.WindowInsetsHelper.navigationBarPadding

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val mainViewModel = viewModel<MainViewModel>()

    val discoverBackStack = mainViewModel.discoverBackStack.collectAsStateWithLifecycle()
    val searchBackStack = mainViewModel.searchBackStack.collectAsStateWithLifecycle()
    val bookingsBackStack = mainViewModel.bookingsBackStack.collectAsStateWithLifecycle()


    val currentKey = mainViewModel.currentKey.collectAsStateWithLifecycle()
    val currentBackStack = when (currentKey.value) {
        BottomNavKey.Discover -> discoverBackStack.value
        BottomNavKey.Search -> searchBackStack.value
        BottomNavKey.Bookings -> bookingsBackStack.value
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier.navigationBarPadding(),
                navigationScreens = BottomNavKey.items,
                selectedScreen = currentKey.value,
                onClick = { key ->
                    if (currentKey.value != key) {
                        mainViewModel.updateCurrentKey(key)
                    } else {
                        when (key) {
                            BottomNavKey.Discover -> mainViewModel.resetBackStack(
                                BottomNavKey.Discover
                            )

                            BottomNavKey.Search -> mainViewModel.resetBackStack(BottomNavKey.Search)
                            BottomNavKey.Bookings -> mainViewModel.resetBackStack(
                                BottomNavKey.Bookings
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        CustomNavDisplay(
            modifier = Modifier,
            currentBackStack = currentBackStack,
            addToBackStack = mainViewModel.addToBackStack,
            onHandleBackPressed = mainViewModel.onHandleBackPressed
        )
    }

    BackHandler(enabled = true) {
        mainViewModel.onHandleBackPressed()
    }
}


@Composable
fun DetailsScreen(key: MainNavKey.Details, onBackPressed: () -> Unit) {
    BackHandler() {
        onBackPressed()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Details Screen")
    }
}

@Composable
fun SettingsScreen(onNavigateToDetails: (MainNavKey.Details) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Text("SettingsScreen ")
            Button(
                onClick = {
                    onNavigateToDetails(MainNavKey.Details(originScreen = "Settings"))
                }
            ) {
                Text("Details Screen")
            }
        }
    }
}

@Composable
fun ProfileScreen(onNavigateToDetails: (MainNavKey.Details) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Text("ProfileScreen")
            Button(
                onClick = {
                    onNavigateToDetails(MainNavKey.Details(originScreen = "Settings"))
                }
            ) {
                Text("Details Screen")
            }
        }
    }
}

@Composable
fun HomeScreen(onNavigateToDetails: (MainNavKey.Details) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Text("HomeScreen")
            Button(
                onClick = {
                    onNavigateToDetails(MainNavKey.Details(originScreen = "Settings"))
                }
            ) {
                Text("Details Screen")
            }
        }
    }
}

