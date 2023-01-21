package com.android.cabifymarketplace.model

import java.math.BigDecimal

data class Discount(
    val name: String,
    val productsCode: List<String>,
    val rule: Pair<String, Int>,
    val type: String,
    val value: BigDecimal,
    val priority: Int
)
