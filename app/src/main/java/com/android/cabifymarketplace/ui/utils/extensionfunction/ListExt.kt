package com.android.cabifymarketplace.ui.utils.extensionfunction

import com.android.cabifymarketplace.model.Product
import com.android.cabifymarketplace.model.ProductOrder

fun MutableList<ProductOrder>.changeQuantity(product: Product, quantity: Int) {
    this.find { it.code == product.code }?.let {
        it.quantity += quantity
    } ?: add(ProductOrder(product.code, product.name, 1, product.price))
}
