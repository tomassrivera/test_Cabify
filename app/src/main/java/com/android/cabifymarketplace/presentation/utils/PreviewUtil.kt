package com.android.cabifymarketplace.presentation.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.cabifymarketplace.core.Constants
import com.android.cabifymarketplace.core.Constants.DESCRIPTION_CART_SUMMARY_TYPE_DISCOUNT
import com.android.cabifymarketplace.core.Constants.DESCRIPTION_CART_SUMMARY_TYPE_NORMAL
import com.android.cabifymarketplace.core.Constants.DESCRIPTION_CART_SUMMARY_TYPE_TOTAL
import com.android.cabifymarketplace.domain.model.Discount
import com.android.cabifymarketplace.domain.model.DiscountRule
import com.android.cabifymarketplace.domain.model.Product
import com.android.cabifymarketplace.domain.model.Products
import com.android.cabifymarketplace.domain.model.db.ProductOrder
import com.android.cabifymarketplace.domain.repository.CabifyMarketplaceRepository
import com.android.cabifymarketplace.domain.usecase.CheckProductExistUseCase
import com.android.cabifymarketplace.domain.usecase.DBUseCases
import com.android.cabifymarketplace.domain.usecase.DeleteCartUseCase
import com.android.cabifymarketplace.domain.usecase.DeleteProductCartUseCase
import com.android.cabifymarketplace.domain.usecase.GetCartUseCase
import com.android.cabifymarketplace.domain.usecase.GetProductQuantityUseCase
import com.android.cabifymarketplace.domain.usecase.GetProductsUseCase
import com.android.cabifymarketplace.domain.usecase.InsertProductCartUseCase
import com.android.cabifymarketplace.domain.usecase.UpdateProductQuantityUseCase
import com.android.cabifymarketplace.presentation.cart.DescriptionCartSummary
import com.android.cabifymarketplace.presentation.products.ProductViewModel
import java.math.BigDecimal

@Suppress("MagicNumber")
object PreviewUtil {

    fun getMockProductsViewModel(): ProductViewModel {
        val cabifyMarketplaceRepository = getFakeCabifyRepository()
        return ProductViewModel(
            GetProductsUseCase(cabifyMarketplaceRepository),
            DBUseCases(
                CheckProductExistUseCase(cabifyMarketplaceRepository),
                DeleteProductCartUseCase(cabifyMarketplaceRepository),
                GetCartUseCase(cabifyMarketplaceRepository),
                GetProductQuantityUseCase(cabifyMarketplaceRepository),
                InsertProductCartUseCase(cabifyMarketplaceRepository),
                UpdateProductQuantityUseCase(cabifyMarketplaceRepository),
                DeleteCartUseCase(cabifyMarketplaceRepository)
            )
        )
    }

    fun getMockDescriptionCartSummary(): List<DescriptionCartSummary> {
        return listOf(
            DescriptionCartSummary("Cabify Voucher (3)", "35.50", DESCRIPTION_CART_SUMMARY_TYPE_NORMAL),
            DescriptionCartSummary("Cabify T-Shirt", "65.00", DESCRIPTION_CART_SUMMARY_TYPE_NORMAL),
            DescriptionCartSummary("Venta al pro mayor", "5.00", DESCRIPTION_CART_SUMMARY_TYPE_DISCOUNT),
            DescriptionCartSummary("2 X 1", "10.50", DESCRIPTION_CART_SUMMARY_TYPE_DISCOUNT),
            DescriptionCartSummary("Total: ", "85.00", DESCRIPTION_CART_SUMMARY_TYPE_TOTAL)
        )
    }

    private fun getFakeCabifyRepository() = object : CabifyMarketplaceRepository {
        override suspend fun getProducts(): Products {
            return Products(
                products = listOf(
                    Product("VOUCHER", "Cabify Voucher", BigDecimal(5)),
                    Product("TSHIRT", "Cabify T-Shirt", BigDecimal(20)),
                    Product("MUG", "Cabify Coffee Mug", BigDecimal(7.5))
                )
            )
        }

        override suspend fun getDiscounts(): List<Discount> {
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

        override fun getProductsCart(): LiveData<List<ProductOrder>> {
            return MutableLiveData(
                listOf<ProductOrder>(
                    ProductOrder("TSHIRT", "Cabify T-Shirt", 4, 20.0),
                    ProductOrder("MUG", "Cabify Coffee Mug", 1, 7.5),
                    ProductOrder("VOUCHER", "Cabify Voucher", 3, 5.0)
                )
            )
        }

        override suspend fun getOrder(): List<ProductOrder> {
            return listOf(
                ProductOrder("TSHIRT", "Cabify T-Shirt", 4, 20.0),
                ProductOrder("MUG", "Cabify Coffee Mug", 1, 7.5)
            )
        }

        override suspend fun insertProduct(product: ProductOrder) {
            // empty function
        }

        override suspend fun deleteProduct(productCode: String) {
            // empty function
        }

        override suspend fun getQuantityByProductCode(code: String): Int {
            return 3
        }

        override suspend fun updateQuantity(code: String, quantity: Int) {
            // empty function
        }

        override suspend fun deleteProducts() {
            // empty function
        }
    }
}
