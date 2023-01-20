package com.android.cabifymarketplace.ui.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

object Formats {

    fun currencyFormat(price: BigDecimal): String {
        return NumberFormat.getCurrencyInstance(Locale("en", "US"))
            .format(price)
    }
}