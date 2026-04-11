package cloud.pensive.feature.map.presentation.di

import cloud.pensive.feature.map.data.di.featureMapDataModule
import cloud.pensive.feature.map.domain.usecase.CheckLocationPermissionUseCase
import cloud.pensive.feature.map.domain.usecase.GetPredefinedLocationsUseCase
import cloud.pensive.feature.map.domain.usecase.GetUserLocationUseCase
import cloud.pensive.feature.map.presentation.util.PermissionRationaleChecker
import cloud.pensive.feature.map.presentation.viewmodel.DiscoverViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin DI module for presentation layer
 * Provides: ViewModels and Use Cases
 * Imports: Data module to get repositories
 *
 * Entry point for entire map feature
 * Include this module in your app's Koin setup
 */
val featureMapPresentationModule = module {

    // Include all data layer dependencies
    includes(featureMapDataModule)

    // ============ USE CASES ============
    /**
     * Provides GetUserLocationUseCase - factory
     * New instance for each dependency
     */
    factory {
        GetUserLocationUseCase(
            locationRepository = get()
        )
    }

    /**
     * Provides CheckLocationPermissionUseCase - factory
     */
    factory {
        CheckLocationPermissionUseCase(
            locationRepository = get()
        )
    }
    

    /**
     * Provides GetPredefinedLocationsUseCase - factory
     */
    factory {
        GetPredefinedLocationsUseCase(
            locationRepository = get()
        )
    }

    factory {
        PermissionRationaleChecker()
    }


    // ============ VIEW MODELS ============
    /**
     * Provides DiscoverViewModel - viewModel scope
     * Creates Android ViewModel (lifecycle-aware)
     * Depends on all use cases
     * Koin injects all use cases automatically
     */
    viewModelOf(::DiscoverViewModel)
}


