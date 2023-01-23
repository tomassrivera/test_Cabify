package com.android.cabifymarketplace.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "products")
data class ProductOrder(
    @PrimaryKey
    val code: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "quantity")
    var quantity: Int,
    @ColumnInfo(name = "price")
    val price: Double
) {
    fun getFinalPrice(): BigDecimal {
        return BigDecimal(price * quantity)
    }
}
