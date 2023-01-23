package com.android.cabifymarketplace.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.android.cabifymarketplace.model.Discount
import com.android.cabifymarketplace.model.Products
import com.android.cabifymarketplace.model.db.ProductOrder

interface CabifyMarketplaceRepository {
    suspend fun getProducts(): Products
    fun getDiscounts(): List<Discount>
    fun getProductsCart(): LiveData<List<ProductOrder>>
    suspend fun insertProduct(product: ProductOrder)
    suspend fun deleteProduct(productCode: String)
    suspend fun checkProductExists(code: String): Boolean
    suspend fun getQuantityByProductCode(code: String): Int
    suspend fun updateQuantity(code: String, quantity: Int)
    suspend fun deleteProducts()
}