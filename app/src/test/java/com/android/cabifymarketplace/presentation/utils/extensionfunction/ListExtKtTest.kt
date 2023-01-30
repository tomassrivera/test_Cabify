package com.android.cabifymarketplace.presentation.utils.extensionfunction

import com.android.cabifymarketplace.utils.MockObjects
import org.junit.Assert
import org.junit.Test

class ListExtKtTest {

    @Test
    fun `existInCart working OK`() {
        val mockOrder = MockObjects.getOrder()
        val tshirtExist = mockOrder.existInCart("TSHIRT")
        val abcExist = mockOrder.existInCart("ABC")

        Assert.assertTrue(tshirtExist)
        Assert.assertFalse(abcExist)
    }

    @Test
    fun `getQuantityByProductCode working OK`() {
        val mockOrder = MockObjects.getOrder()
        val voucherQuantity = mockOrder.getQuantityByProductCode("VOUCHER")
        val abcQuantity = mockOrder.getQuantityByProductCode("ABC")

        Assert.assertEquals("4", voucherQuantity)
        Assert.assertEquals("", abcQuantity)
    }
}
