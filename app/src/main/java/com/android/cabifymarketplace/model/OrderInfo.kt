package com.android.cabifymarketplace.model

import java.math.BigDecimal

data class OrderInfo(
    val productsCodeSelected: Map<String, Int> = emptyMap(),
    val price: BigDecimal? = null
)