package com.android.cabifymarketplace.ui.utils

import com.android.cabifymarketplace.model.Discount
import com.android.cabifymarketplace.model.db.ProductOrder
import com.android.cabifymarketplace.ui.utils.Constants.Companion.DISCOUNT_RULE_MORE_THAN
import com.android.cabifymarketplace.ui.utils.Constants.Companion.DISCOUNT_RULE_QUANTITY_X_QUANTITY
import com.android.cabifymarketplace.ui.utils.Constants.Companion.DISCOUNT_TYPE_LESS_PRICE
import com.android.cabifymarketplace.ui.utils.Constants.Companion.DISCOUNT_TYPE_LESS_PRICE_EACH_ITEM
import com.android.cabifymarketplace.ui.utils.Constants.Companion.DISCOUNT_TYPE_PERCENTAGE
import java.math.BigDecimal

object DiscountUtil {

    fun discountsCalculate(
        discountList: List<Discount>,
        products: List<ProductOrder>?
    ): List<Pair<String, BigDecimal>> {
        val discountResume = mutableListOf<Pair<String, BigDecimal>>()
        products?.forEach { product ->
            discountList.sortedBy { x -> x.priority }.forEach { discount ->
                if (discount.productsCode.contains(product.code) && ruleCheck(product.quantity, discount.rule)) {
                    discountPriceCalculator(product, discount)?.let {
                        discountResume.add(Pair(discount.name, it))
                    }
                }
            }
        }
        return discountResume
    }

    private fun ruleCheck(productQuantity: Int, rule: Pair<String, Int>): Boolean {
        when (rule.first) {
            DISCOUNT_RULE_MORE_THAN -> {
                return productQuantity > rule.second
            }
            DISCOUNT_RULE_QUANTITY_X_QUANTITY -> {
                return productQuantity >= rule.second
            }
        }
        return false
    }

    private fun discountPriceCalculator(product: ProductOrder, discount: Discount): BigDecimal? {
        return when (discount.rule.first) {
            DISCOUNT_RULE_MORE_THAN -> {
                getDiscountFromDiscountType(discount, product)
            }
            DISCOUNT_RULE_QUANTITY_X_QUANTITY -> {
                val finalProduct = product.copy(quantity = product.quantity - (product.quantity % discount.rule.second))
                getDiscountFromDiscountType(discount, finalProduct)
            }
            else -> {
                null
            }
        }
    }

    private fun getDiscountFromDiscountType(discount: Discount, product: ProductOrder): BigDecimal {
        return when (discount.type) {
            DISCOUNT_TYPE_LESS_PRICE -> {
                discount.value
            }
            DISCOUNT_TYPE_LESS_PRICE_EACH_ITEM -> {
                discount.value.multiply(product.quantity.toBigDecimal())
            }
            DISCOUNT_TYPE_PERCENTAGE -> {
                (product.price * product.quantity).toBigDecimal().multiply(discount.value)
            }
            else -> {
                BigDecimal.ZERO
            }
        }
    }
}