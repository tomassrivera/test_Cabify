package com.android.cabifymarketplace.presentation.utils.extensionfunction

import com.android.cabifymarketplace.domain.model.db.ProductOrder

fun List<ProductOrder>?.existInCart(code: String): Boolean {
    return this?.find { it.code == code } != null
}

fun List<ProductOrder>?.getQuantityByProductCode(code: String): String {
    return this?.find { it.code == code }?.quantity?.toString() ?: ""
}
