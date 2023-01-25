package com.android.cabifymarketplace.data.datasource.api

import com.android.cabifymarketplace.BuildConfig
import com.android.cabifymarketplace.domain.model.Discount
import com.android.cabifymarketplace.domain.model.Products
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface CabifyMarketplaceService {

    @GET("{apiKey}/raw/Products.json")
    suspend fun getProducts(@Path("apiKey") apiKey: String = BuildConfig.API_KEY): Products

    @GET
    suspend fun getDiscounts(@Url fullUrl: String = BuildConfig.URL_DISCOUNTS): List<Discount>
}
