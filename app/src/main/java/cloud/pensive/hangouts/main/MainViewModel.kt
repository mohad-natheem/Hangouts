package cloud.pensive.hangouts.main

import BottomNavKey
import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _discoverStack = MutableStateFlow(listOf<NavKey>(BottomNavKey.Discover))
    val discoverBackStack = _discoverStack.asStateFlow()


    private val _bookingsBackStack = MutableStateFlow(listOf<NavKey>(BottomNavKey.Bookings))
    val bookingsBackStack = _bookingsBackStack.asStateFlow()

    private val _currentKey = MutableStateFlow<BottomNavKey>(BottomNavKey.Discover)
    val currentKey = _currentKey.asStateFlow()


    fun updateCurrentKey(key: BottomNavKey) {
        _currentKey.update {
            key
        }
    }

    val addToBackStack: (NavKey) -> Unit = { navKey ->
        when (currentKey.value) {
            BottomNavKey.Discover -> {
                _discoverStack.update {
                    it + navKey
                }
            }

            BottomNavKey.Bookings -> {
                _bookingsBackStack.update {
                    it + navKey
                }
            }
        }
    }

    fun popBackStack(navKey: BottomNavKey) {
        when (navKey) {
            BottomNavKey.Discover -> {
                _discoverStack.update { stack ->
                    if (stack.size > 1) {
                        stack.dropLast(1)
                    } else {
                        stack
                    }
                }
            }


            BottomNavKey.Bookings -> {
                _bookingsBackStack.update { stack ->
                    if (stack.size > 1) {
                        stack.dropLast(1)
                    } else {
                        stack
                    }
                }
            }
        }
    }

    val resetBackStack: (navKey: BottomNavKey) -> Unit = {
        when (it) {
            BottomNavKey.Discover -> {
                _discoverStack.update {
                    if (it.size > 1) listOf(BottomNavKey.Discover) else it
                }
            }


            BottomNavKey.Bookings -> {
                _bookingsBackStack.update {
                    if (it.size > 1) listOf(BottomNavKey.Bookings) else it
                }
            }
        }
    }

    val onHandleBackPressed: () -> Unit = {
        onBackPressed(
            currentBottomKey = currentKey.value,
            bookingsBackStackSize = bookingsBackStack.value.size,
            onSetHomeKey = {
                updateCurrentKey(BottomNavKey.Discover)
            },
            onPopHomeBackStack = {
                popBackStack(BottomNavKey.Discover)
            },
            onPopBookingsBackStack = {
                popBackStack(BottomNavKey.Bookings)
            }
        )
    }

    private fun onBackPressed(
        currentBottomKey: BottomNavKey,
        bookingsBackStackSize: Int,
        onSetHomeKey: () -> Unit,
        onPopHomeBackStack: () -> Unit,
        onPopBookingsBackStack: () -> Unit
    ) {
        when (currentBottomKey) {
            BottomNavKey.Discover -> {
                onPopHomeBackStack()
            }


            BottomNavKey.Bookings -> {
                if (bookingsBackStackSize > 1) {
                    onPopBookingsBackStack()
                } else {
                    onSetHomeKey()
                }
            }
        }
    }
}