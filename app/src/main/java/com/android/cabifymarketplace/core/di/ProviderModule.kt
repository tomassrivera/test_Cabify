package com.android.cabifymarketplace.core.di

import com.android.cabifymarketplace.data.repository.CabifyMarketplaceRepositoryImp
import com.android.cabifymarketplace.domain.repository.CabifyMarketplaceRepository
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
