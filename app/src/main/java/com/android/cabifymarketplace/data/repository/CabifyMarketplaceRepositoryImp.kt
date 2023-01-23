package com.android.cabifymarketplace.data.repository

import androidx.lifecycle.LiveData
import com.android.cabifymarketplace.data.DataSource.discounts
import com.android.cabifymarketplace.data.api.CabifyMarketplaceService
import com.android.cabifymarketplace.data.db.ProductDAO
import com.android.cabifymarketplace.model.Discount
import com.android.cabifymarketplace.model.Products
import com.android.cabifymarketplace.model.db.ProductOrder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CabifyMarketplaceRepositoryImp @Inject constructor(
    private val networkDataSource: CabifyMarketplaceService,
    private val localDataSource: ProductDAO
): CabifyMarketplaceRepository {
    override suspend fun getProducts(): Products {
        return networkDataSource.getProducts()
    }

    override fun getDiscounts(): List<Discount> {
        return discounts
    }

    override fun getProductsCart(): LiveData<List<ProductOrder>> {
        return localDataSource.getProducts()
    }

    override suspend fun insertProduct(product: ProductOrder) {
        localDataSource.insertProduct(product)
    }

    override suspend fun deleteProduct(productCode: String) {
        localDataSource.deleteProduct(productCode)
    }

    override suspend fun checkProductExists(code: String): Boolean {
        return localDataSource.checkProductExists(code) > 0
    }

    override suspend fun getQuantityByProductCode(code: String): Int {
        return localDataSource.getQuantityByProductCode(code)
    }

    override suspend fun updateQuantity(code: String, quantity: Int) {
        localDataSource.updateQuantity(code, quantity)
    }

    override suspend fun deleteProducts() {
        localDataSource.deleteProducts()
    }
}