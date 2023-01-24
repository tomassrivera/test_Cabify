package com.android.cabifymarketplace.domain.model

import com.android.cabifymarketplace.domain.model.db.ProductOrder
import java.math.BigDecimal

data class Product(
    val code: String,
    val name: String,
    val price: BigDecimal
) {
    fun convertToProductOrder(): ProductOrder {
        return ProductOrder(
            code = code,
            name = name,
            price = price.toDouble(),
            quantity = 1
        )
    }
}
