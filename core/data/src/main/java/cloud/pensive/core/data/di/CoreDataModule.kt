package cloud.pensive.core.data.di

import cloud.pensive.core.data.datastore.DataStoreManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val coreDataModule = module {
    single {
        DataStoreManager(androidApplication())
    }
}