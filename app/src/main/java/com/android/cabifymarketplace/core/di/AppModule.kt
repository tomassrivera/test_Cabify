package com.android.cabifymarketplace.core.di

import android.content.Context
import androidx.room.Room
import com.android.cabifymarketplace.core.Constants.DATABASE_NAME
import com.android.cabifymarketplace.data.datasource.api.CabifyMarketplaceService
import com.android.cabifymarketplace.data.datasource.db.DbDataSource
import com.android.cabifymarketplace.domain.repository.CabifyMarketplaceRepository
import com.android.cabifymarketplace.domain.usecase.CheckProductExistUseCase
import com.android.cabifymarketplace.domain.usecase.DBUseCases
import com.android.cabifymarketplace.domain.usecase.DeleteCartUseCase
import com.android.cabifymarketplace.domain.usecase.DeleteProductCartUseCase
import com.android.cabifymarketplace.domain.usecase.GetCartUseCase
import com.android.cabifymarketplace.domain.usecase.GetDiscountsUseCase
import com.android.cabifymarketplace.domain.usecase.GetProductQuantityUseCase
import com.android.cabifymarketplace.domain.usecase.GetProductsUseCase
import com.android.cabifymarketplace.domain.usecase.InsertProductCartUseCase
import com.android.cabifymarketplace.domain.usecase.UpdateProductQuantityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = "https://gist.githubusercontent.com/palcalde/".toHttpUrl()

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: HttpUrl): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            DbDataSource::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDAO(db: DbDataSource) = db.productDAO()

    @Singleton
    @Provides
    fun cabifyMarketplaceServiceProvider(retrofit: Retrofit): CabifyMarketplaceService =
        retrofit.create(CabifyMarketplaceService::class.java)

    @Provides
    @Singleton
    fun provideDBUseCases(repository: CabifyMarketplaceRepository): DBUseCases {
        return DBUseCases(
            checkProductExistUseCase = CheckProductExistUseCase(repository),
            deleteProductCartUseCase = DeleteProductCartUseCase(repository),
            getCartUseCase = GetCartUseCase(repository),
            getProductQuantityUseCase = GetProductQuantityUseCase(repository),
            insertProductCartUseCase = InsertProductCartUseCase(repository),
            updateProductQuantityUseCase = UpdateProductQuantityUseCase(repository),
            deleteCartUseCase = DeleteCartUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideGetProductsUseCase(repository: CabifyMarketplaceRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDiscountsUseCase(repository: CabifyMarketplaceRepository): GetDiscountsUseCase {
        return GetDiscountsUseCase(repository)
    }
}
