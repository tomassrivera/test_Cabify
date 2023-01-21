package com.android.cabifymarketplace.data.repository

import com.android.cabifymarketplace.model.Discount
import com.android.cabifymarketplace.model.Products

interface CabifyMarketplaceRepository {
    suspend fun getProducts(): Products
    fun getDiscounts(): List<Discount>
}