package cloud.pensive.hangouts

import android.app.Application
import cloud.pensive.core.data.di.coreDataModule
import cloud.pensive.feature.map.presentation.di.featureMapViewmodelModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

@HiltAndroidApp
class HangoutsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@HangoutsApplication)
            modules(
                coreDataModule,
                featureMapViewmodelModule,

                )
        }
    }
}