package com.android.cabifymarketplace.data.repository

import com.android.cabifymarketplace.data.CabifyMarketplaceService
import com.android.cabifymarketplace.data.DataSource.discounts
import com.android.cabifymarketplace.model.Discount
import com.android.cabifymarketplace.model.Products
import javax.inject.Inject

class CabifyMarketplaceRepositoryImp @Inject constructor(
    private val cabifyMarketplaceService: CabifyMarketplaceService
): CabifyMarketplaceRepository {
    override suspend fun getProducts(): Products {
        return cabifyMarketplaceService.getProducts()
    }

    override fun getDiscounts(): List<Discount> {
        return discounts
    }
}