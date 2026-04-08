import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.ViewModel
import cloud.pensive.hangouts.R
import cloud.pensive.hangouts.domain.model.OnboardingModel

class OnBoardingViewModel : ViewModel() {
    val onboardingData = listOf(
        OnboardingModel(
            title = "Discover Clubs Nearby",
            description = "Find book clubs, music groupsm and communities happening around you.",
            imageRes = R.drawable.ic_map
        ),
        OnboardingModel(
            title = "Explore Communities",
            description = "Learn what each club is about and discover people who share your interests.",
            imageRes = R.drawable.ic_group
        ),
        OnboardingModel(
            title = "Join and Hangout",
            description = "Join clubs you like and meet people in your city.",
            imageRes = R.drawable.ic_check_circle
        )
    )
}




