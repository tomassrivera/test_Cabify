package com.android.cabifymarketplace.model

import java.math.BigDecimal

data class ProductOrder(
    val code: String,
    val name: String,
    var quantity: Int,
    val price: BigDecimal
) {
    fun getFinalPrice(): BigDecimal {
        return price.multiply(BigDecimal(quantity))
    }
}
