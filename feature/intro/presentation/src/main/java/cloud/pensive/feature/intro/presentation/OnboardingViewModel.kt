import androidx.lifecycle.ViewModel
import cloud.pensive.core.presentation.R
import cloud.pensive.feature.intro.presentation.OnboardingModel

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




