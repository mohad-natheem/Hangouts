package cloud.pensive.feature.map.presentation.di

import cloud.pensive.feature.map.presentation.DiscoverViewModel
import cloud.pensive.feature.map.presentation.LocationManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureMapViewmodelModule = module {
    viewModelOf(::DiscoverViewModel)
    single {
        LocationManager(androidApplication())
    }
}
