package com.android.cabifymarketplace.presentation.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

object Formats {

    fun currencyFormat(price: BigDecimal): String {
        return NumberFormat.getCurrencyInstance(Locale("en", "US"))
            .format(price)
    }
}
