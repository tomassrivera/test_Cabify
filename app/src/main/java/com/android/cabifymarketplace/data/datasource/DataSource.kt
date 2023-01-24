package com.android.cabifymarketplace.data.datasource

import com.android.cabifymarketplace.core.Constants.DISCOUNT_RULE_MORE_THAN
import com.android.cabifymarketplace.core.Constants.DISCOUNT_RULE_QUANTITY_X_QUANTITY
import com.android.cabifymarketplace.core.Constants.DISCOUNT_TYPE_LESS_PRICE_EACH_ITEM
import com.android.cabifymarketplace.core.Constants.DISCOUNT_TYPE_PERCENTAGE
import com.android.cabifymarketplace.domain.model.Discount
import com.android.cabifymarketplace.domain.model.DiscountRule
import java.math.BigDecimal

object DataSource {
    val discounts = listOf(
        Discount(
            "Compra al por mayor",
            listOf("TSHIRT"),
            DiscountRule(DISCOUNT_RULE_MORE_THAN, 2),
            DISCOUNT_TYPE_LESS_PRICE_EACH_ITEM,
            BigDecimal(1.0),
            3
        ),
        Discount(
            "2 X 1",
            listOf("VOUCHER"),
            DiscountRule(DISCOUNT_RULE_QUANTITY_X_QUANTITY, 2),
            DISCOUNT_TYPE_PERCENTAGE,
            BigDecimal(0.5),
            3
        )
    )
}
