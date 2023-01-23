package com.android.cabifymarketplace.di

import android.content.Context
import androidx.room.Room
import com.android.cabifymarketplace.data.api.CabifyMarketplaceService
import com.android.cabifymarketplace.data.db.DbDataSource
import com.android.cabifymarketplace.ui.utils.Constants.Companion.DATABASE_NAME
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
    fun provideMoviesDAO(db: DbDataSource) = db.productDAO()


    @Singleton
    @Provides
    fun cabifyMarketplaceServiceProvider(retrofit: Retrofit): CabifyMarketplaceService =
        retrofit.create(CabifyMarketplaceService::class.java)

}








