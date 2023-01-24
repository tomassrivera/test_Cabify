package com.android.cabifymarketplace.data.repository

import androidx.lifecycle.LiveData
import com.android.cabifymarketplace.data.datasource.api.CabifyMarketplaceService
import com.android.cabifymarketplace.data.datasource.db.ProductDAO
import com.android.cabifymarketplace.domain.model.Discount
import com.android.cabifymarketplace.domain.model.Products
import com.android.cabifymarketplace.domain.model.db.ProductOrder
import com.android.cabifymarketplace.domain.repository.CabifyMarketplaceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CabifyMarketplaceRepositoryImp @Inject constructor(
    private val networkDataSource: CabifyMarketplaceService,
    private val localDataSource: ProductDAO
) : CabifyMarketplaceRepository {
    override suspend fun getProducts(): Products {
        return networkDataSource.getProducts()
    }

    override suspend fun getDiscounts(): List<Discount> {
        return networkDataSource.getDiscounts()
    }

    override suspend fun getOrder(): List<ProductOrder> {
        return localDataSource.getOrder()
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
