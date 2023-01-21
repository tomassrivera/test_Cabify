package com.android.cabifymarketplace.model

import kotlinx.coroutines.flow.update
import java.math.BigDecimal

data class OrderInfo(
    var productsCodeSelected: MutableList<ProductOrder> = mutableListOf()
) {
    fun getItemQuantity(productCode: String): Int {
        return productsCodeSelected.find { x -> x.code == productCode }?.quantity ?: 0
    }

    fun getTotalQuantity(): Int {
        return productsCodeSelected.sumOf { it.quantity }
    }
}