package com.android.cabifymarketplace.di

import com.android.cabifymarketplace.data.CabifyMarketplaceService
import com.android.cabifymarketplace.data.repository.CabifyMarketplaceRepository
import com.android.cabifymarketplace.data.repository.CabifyMarketplaceRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providerNewsRepository(provider: CabifyMarketplaceService): CabifyMarketplaceRepository =
        CabifyMarketplaceRepositoryImp(provider)
}








