package com.android.cabifymarketplace.presentation.utils

import com.android.cabifymarketplace.presentation.utils.DiscountUtil.discountsCalculate
import com.android.cabifymarketplace.utils.MockObjects
import org.junit.Assert
import org.junit.Test

class DiscountUtilTest {

    @Test
    fun `discountsCalculate working OK`() {
        val order = MockObjects.getOrder()
        val discounts = MockObjects.getDiscounts()
        val discountsCalculate = discountsCalculate(discounts, order)

        Assert.assertEquals(2, discountsCalculate.size)
        Assert.assertEquals(13.0, discountsCalculate.sumOf { it.second }.toDouble(), 0.0)
    }
}
