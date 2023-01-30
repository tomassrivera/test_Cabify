package com.android.cabifymarketplace.presentation.utils

import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

class FormatsTest {

    @Test
    fun `currencyFormat working OK`() {
        val currencyFormat = Formats.currencyFormat(BigDecimal(34.212665))

        Assert.assertEquals("$34.21", currencyFormat)
    }
}
