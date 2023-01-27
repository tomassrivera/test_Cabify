package com.android.cabifymarketplace.utils

import com.android.cabifymarketplace.core.Constants
import com.android.cabifymarketplace.domain.model.Discount
import com.android.cabifymarketplace.domain.model.DiscountRule
import com.android.cabifymarketplace.domain.model.Product
import com.android.cabifymarketplace.domain.model.Products
import com.android.cabifymarketplace.domain.model.db.ProductOrder
import java.math.BigDecimal

object MockObjects {

    fun getProductOrder(): ProductOrder {
        return ProductOrder("TSHIRT", "Cabify T-Shirt", 2, 20.0)
    }

    fun getOrder(): List<ProductOrder> {
        return listOf(
            ProductOrder("VOUCHER", "Cabify Voucher", 4, 5.0),
            ProductOrder("TSHIRT", "Cabify T-Shirt", 3, 20.0),
            ProductOrder("MUG", "Cabify Coffee Mug", 2, 7.5)
        )
    }

    fun getDiscounts(): List<Discount> {
        return listOf(
            Discount(
                "Compra al por mayor",
                listOf("TSHIRT"),
                DiscountRule(Constants.DISCOUNT_RULE_MORE_THAN, 2),
                Constants.DISCOUNT_TYPE_LESS_PRICE_EACH_ITEM,
                BigDecimal(1.0),
                3
            ),
            Discount(
                "2 X 1",
                listOf("VOUCHER"),
                DiscountRule(Constants.DISCOUNT_RULE_QUANTITY_X_QUANTITY, 2),
                Constants.DISCOUNT_TYPE_PERCENTAGE,
                BigDecimal(0.5),
                3
            )
        )
    }
    fun getProducts(): Products {
        return Products(
            products = listOf(
                Product("VOUCHER", "Cabify Voucher", BigDecimal(5)),
                Product("TSHIRT", "Cabify T-Shirt", BigDecimal(20)),
                Product("MUG", "Cabify Coffee Mug", BigDecimal(7.5))
            )
        )
    }
}
