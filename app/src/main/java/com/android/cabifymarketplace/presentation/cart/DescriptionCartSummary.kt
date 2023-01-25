package com.android.cabifymarketplace.presentation.cart

data class DescriptionCartSummary(
    val description: String,
    val price: String,
    val type: String,
    val quantity: Int = 0
)
