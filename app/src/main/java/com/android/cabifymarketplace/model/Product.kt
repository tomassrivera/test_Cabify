package com.android.cabifymarketplace.model

import java.math.BigDecimal

data class Product(
    val code: String,
    val name: String,
    val price: BigDecimal
)
