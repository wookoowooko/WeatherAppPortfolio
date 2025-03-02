package io.wookoo.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.data.repo.DataStoreRepoImpl
import io.wookoo.data.repo.MasterRepoImpl
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.IMasterWeatherRepo

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsMasterRepo(
        masterRepo: MasterRepoImpl,
    ): IMasterWeatherRepo

    @Binds
    fun bindsDataStoreRepo(
        dataStoreRepo: DataStoreRepoImpl,
    ): IDataStoreRepo
}
