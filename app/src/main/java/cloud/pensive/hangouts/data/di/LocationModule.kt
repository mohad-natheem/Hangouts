package cloud.pensive.hangouts.data.di

import android.content.Context
import android.location.LocationProvider
import cloud.pensive.hangouts.presentation.discover.LocationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationProvider(
        @ApplicationContext context: Context
    ): LocationManager {
        return LocationManager(context)
    }
}