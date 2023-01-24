package com.android.cabifymarketplace.domain.model

import java.math.BigDecimal

data class Discount(
    val name: String,
    val productsCode: List<String>,
    val rule: DiscountRule,
    val type: String,
    val value: BigDecimal,
    val priority: Int
)
