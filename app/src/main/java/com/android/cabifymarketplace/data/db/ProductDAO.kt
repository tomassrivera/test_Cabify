package com.android.cabifymarketplace.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.cabifymarketplace.model.db.ProductOrder

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productOrder: ProductOrder)

    @Query("SELECT count(*) FROM products WHERE products.code = :code")
    suspend fun checkProductExists(code: String): Int

    @Query("SELECT products.quantity FROM products WHERE products.code = :code")
    suspend fun getQuantityByProductCode(code: String): Int

    @Query("UPDATE products SET quantity = quantity + :quantity  WHERE products.code = :code")
    suspend fun updateQuantity(code: String, quantity: Int)

    @Query("SELECT * FROM products")
    fun getProducts(): LiveData<List<ProductOrder>>

    @Query("DELETE FROM products WHERE products.code = :productCode")
    suspend fun deleteProduct(productCode: String)

    @Query("DELETE FROM products")
    suspend fun deleteProducts()
}
