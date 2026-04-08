import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cloud.pensive.hangouts.presentation.onboarding.composables.OnboardingBottomBar
import cloud.pensive.hangouts.presentation.onboarding.composables.OnboardingItem
import cloud.pensive.hangouts.presentation.utils.WindowInsetsHelper.navigationBarPadding
import cloud.pensive.hangouts.presentation.utils.horizontalPadding
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onBoardingComplete: () -> Unit,
    onBoardingViewModel: OnBoardingViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState =
        rememberPagerState(initialPage = 0, pageCount = { onBoardingViewModel.onboardingData.size })

    fun onNextClick() {
        if (pagerState.currentPage < onBoardingViewModel.onboardingData.size - 1) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        } else {
            onBoardingComplete()
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            OnboardingBottomBar(
                modifier = Modifier
                    .horizontalPadding(16.dp)
                    .navigationBarPadding(),
                onNextClick = ::onNextClick,
                currentPage = pagerState.currentPage,
                pageCount = onBoardingViewModel.onboardingData.size,
                isLastPage = pagerState.currentPage == onBoardingViewModel.onboardingData.size - 1
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) { page ->

            OnboardingItem(
                page = onBoardingViewModel.onboardingData[page],
                isVisible = pagerState.currentPage == page
            )
        }
        Text(
            text = "Hi"
        )
    }

}
