package com.android.cabifymarketplace.di

import com.android.cabifymarketplace.data.repository.CabifyMarketplaceRepository
import com.android.cabifymarketplace.data.repository.CabifyMarketplaceRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {
    @Binds
    @Singleton
    abstract fun bindCabifyMarketplaceRepository(mainRepository: CabifyMarketplaceRepositoryImp): CabifyMarketplaceRepository
}








