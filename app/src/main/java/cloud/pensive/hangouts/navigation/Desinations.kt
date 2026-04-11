import androidx.compose.runtime.saveable.Saver
import androidx.navigation3.runtime.NavKey
import cloud.pensive.hangouts.R
import kotlinx.serialization.Serializable

sealed interface BottomNavKey : NavKey {
    val icon: Int
    val label: String

    @Serializable
    data object Discover : BottomNavKey {
        override val icon: Int = R.drawable.ic_explore
        override val label: String = "Home"
    }

    @Serializable
    data object Search : BottomNavKey {
        override val icon: Int = R.drawable.ic_search
        override val label: String = "Profile"
    }

    @Serializable
    data object Bookings : BottomNavKey {
        override val icon: Int = R.drawable.ic_mail
        override val label: String = "Settings"
    }

    companion object {
        val items = listOf(Discover, Search, Bookings)

        val stateSaver = Saver<BottomNavKey, String>(
            save = { it::class.qualifiedName },
            restore = { qualifiedClass ->
                items.firstOrNull { it::class.qualifiedName == qualifiedClass }
                    ?: Discover
            }
        )
    }
}

interface MainNavKey : NavKey {
    @Serializable
    data class Details(val originScreen: String) : MainNavKey
}
