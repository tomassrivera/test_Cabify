package com.android.cabifymarketplace.data

import com.android.cabifymarketplace.model.Product
import com.android.cabifymarketplace.model.Products
import retrofit2.http.GET

private const val API_KEY = "6c19259bd32dd6aafa327fa557859c2f"

interface CabifyMarketplaceService {

    @GET("$API_KEY/raw/Products.json")
    suspend fun getProducts(): Products
}