package com.android.cabifymarketplace.domain.repository

import androidx.lifecycle.LiveData
import com.android.cabifymarketplace.domain.model.Discount
import com.android.cabifymarketplace.domain.model.Products
import com.android.cabifymarketplace.domain.model.db.ProductOrder

interface CabifyMarketplaceRepository {
    suspend fun getProducts(): Products
    suspend fun getDiscounts(): List<Discount>
    fun getProductsCart(): LiveData<List<ProductOrder>>
    suspend fun getOrder(): List<ProductOrder>
    suspend fun insertProduct(product: ProductOrder)
    suspend fun deleteProduct(productCode: String)
    suspend fun getQuantityByProductCode(code: String): Int
    suspend fun updateQuantity(code: String, quantity: Int)
    suspend fun deleteProducts()
}
