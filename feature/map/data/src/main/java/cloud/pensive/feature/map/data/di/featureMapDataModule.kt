package cloud.pensive.feature.map.data.di

import android.content.Context
import cloud.pensive.feature.map.data.manager.LocationFetcher
import cloud.pensive.feature.map.data.manager.LocationPermissionManager
import cloud.pensive.feature.map.data.repository.LocationRepositoryImpl
import cloud.pensive.feature.map.data.source.LocalLocationDataSource
import cloud.pensive.feature.map.data.source.RemoteLocationDataSource
import cloud.pensive.feature.map.domain.repository.LocationRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Koin DI module for data layer dependencies
 * Provides: Managers, DataSources, Repository implementation
 * Used by: Presentation module via Koin
 *
 * Scope:
 * - single: One instance for entire app (managers, repository)
 * - factory: New instance when needed (data sources)
 */
val featureMapDataModule = module {

    // ============ MANAGERS ============
    /**
     * Provides LocationFetcher - singleton
     * Wraps FusedLocationProviderClient
     */
    single {
        LocationFetcher(context = androidApplication())
    }

    /**
     * Provides LocationPermissionManager - singleton
     * Handles permission checks via Android APIs
     */
    single {
        LocationPermissionManager(context = androidApplication())
    }

    // ============ DATA SOURCES ============
    /**
     * Provides RemoteLocationDataSource - factory
     * Fetches user's real-time device location
     * New instance for each injection
     */
    factory {
        RemoteLocationDataSource(
            locationFetcher = get()
        )
    }

    /**
     * Provides LocalLocationDataSource - singleton
     * Returns hardcoded predefined locations
     * One instance is sufficient
     */
    single {
        LocalLocationDataSource()
    }

    // ============ REPOSITORY ============
    /**
     * Provides LocationRepository implementation - singleton
     * Registered as LocationRepository interface (dependency inversion)
     * Depends on both data sources and permission manager
     *
     * Key: Registered as interface type, allows domain to depend on interface
     */
    single<LocationRepository> {
        LocationRepositoryImpl(
            remoteDataSource = get<RemoteLocationDataSource>(),
            localDataSource = get<LocalLocationDataSource>(),
            permissionManager = get()
        )
    }
}

