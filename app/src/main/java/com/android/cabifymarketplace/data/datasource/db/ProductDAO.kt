package com.android.cabifymarketplace.data.datasource.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.cabifymarketplace.domain.model.db.ProductOrder

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productOrder: ProductOrder)

    @Query("SELECT products.quantity FROM products WHERE products.code = :code")
    suspend fun getQuantityByProductCode(code: String): Int?

    @Query("UPDATE products SET quantity = quantity + :quantity  WHERE products.code = :code")
    suspend fun updateQuantity(code: String, quantity: Int)

    @Query("SELECT * FROM products")
    fun getProducts(): LiveData<List<ProductOrder>>

    @Query("SELECT * FROM products")
    fun getOrder(): List<ProductOrder>

    @Query("DELETE FROM products WHERE products.code = :productCode")
    suspend fun deleteProduct(productCode: String)

    @Query("DELETE FROM products")
    suspend fun deleteProducts()
}
