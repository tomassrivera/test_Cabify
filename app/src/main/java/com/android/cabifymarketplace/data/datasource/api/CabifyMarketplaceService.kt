package com.android.cabifymarketplace.data.datasource.api

import com.android.cabifymarketplace.domain.model.Discount
import com.android.cabifymarketplace.domain.model.Products
import retrofit2.http.GET
import retrofit2.http.Url

private const val API_KEY = "6c19259bd32dd6aafa327fa557859c2f"
private const val URL_DISCOUNTS = "https://mocki.io/v1/a10cac92-dff8-46db-b038-8dd945f58c9a"

interface CabifyMarketplaceService {

    @GET("$API_KEY/raw/Products.json")
    suspend fun getProducts(): Products

    @GET
    suspend fun getDiscounts(@Url fullUrl: String = URL_DISCOUNTS): List<Discount>
}
