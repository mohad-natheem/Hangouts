package cloud.pensive.hangouts

import OnboardingScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import cloud.pensive.core.data.datastore.DataStoreManager
import cloud.pensive.hangouts.presentation.main.MainScreen
import cloud.pensive.hangouts.ui.theme.HangoutsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HangoutsTheme {
                val context = LocalContext.current
                val dataStoreManager: DataStoreManager by inject()
                val isOnBoardCompleted =
                    dataStoreManager.onboardingCompleted.collectAsState(false)


                LaunchedEffect(isOnBoardCompleted.value) {
                    Timber.tag("natheem").i("Onboarding completed: ${isOnBoardCompleted.value}")

                }
                fun onBoardingComplete() {
                    lifecycleScope.launch {
                        dataStoreManager.setOnboardingCompleted(true)
                    }
                }


                if (!isOnBoardCompleted.value) {
                    OnboardingScreen(
                        onBoardingComplete = {
                            onBoardingComplete()
                        }
                    )
                } else {
                    MainScreen()

                }
            }
        }
    }
}