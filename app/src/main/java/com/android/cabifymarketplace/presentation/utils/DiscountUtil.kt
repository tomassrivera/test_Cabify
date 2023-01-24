package com.android.cabifymarketplace.presentation.utils

import com.android.cabifymarketplace.core.Constants.DISCOUNT_RULE_MORE_THAN
import com.android.cabifymarketplace.core.Constants.DISCOUNT_RULE_QUANTITY_X_QUANTITY
import com.android.cabifymarketplace.core.Constants.DISCOUNT_TYPE_LESS_PRICE
import com.android.cabifymarketplace.core.Constants.DISCOUNT_TYPE_LESS_PRICE_EACH_ITEM
import com.android.cabifymarketplace.core.Constants.DISCOUNT_TYPE_PERCENTAGE
import com.android.cabifymarketplace.domain.model.Discount
import com.android.cabifymarketplace.domain.model.DiscountRule
import com.android.cabifymarketplace.domain.model.db.ProductOrder
import java.math.BigDecimal

object DiscountUtil {

    fun discountsCalculate(
        discountList: List<Discount>?,
        products: List<ProductOrder>?
    ): List<Pair<String, BigDecimal>> {
        val discountResume = mutableListOf<Pair<String, BigDecimal>>()
        products?.forEach { product ->
            discountList?.sortedBy { x -> x.priority }?.forEach { discount ->
                if (discount.productsCode.contains(product.code) && ruleCheck(product.quantity, discount.rule)) {
                    discountPriceCalculator(product, discount)?.let {
                        discountResume.add(Pair(discount.name, it))
                    }
                }
            }
        }
        return discountResume
    }

    private fun ruleCheck(productQuantity: Int, rule: DiscountRule): Boolean {
        when (rule.code) {
            DISCOUNT_RULE_MORE_THAN -> {
                return productQuantity > rule.value
            }
            DISCOUNT_RULE_QUANTITY_X_QUANTITY -> {
                return productQuantity >= rule.value
            }
        }
        return false
    }

    private fun discountPriceCalculator(product: ProductOrder, discount: Discount): BigDecimal? {
        return when (discount.rule.code) {
            DISCOUNT_RULE_MORE_THAN -> {
                getDiscountFromDiscountType(discount, product)
            }
            DISCOUNT_RULE_QUANTITY_X_QUANTITY -> {
                val finalProduct = product.copy(quantity = product.quantity - (product.quantity % discount.rule.value))
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
