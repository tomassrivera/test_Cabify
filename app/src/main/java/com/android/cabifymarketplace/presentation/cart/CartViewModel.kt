package com.android.cabifymarketplace.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cabifymarketplace.core.Constants.DESCRIPTION_CART_SUMMARY_TYPE_DISCOUNT
import com.android.cabifymarketplace.core.Constants.DESCRIPTION_CART_SUMMARY_TYPE_NORMAL
import com.android.cabifymarketplace.core.Constants.DESCRIPTION_CART_SUMMARY_TYPE_TOTAL
import com.android.cabifymarketplace.core.Resource
import com.android.cabifymarketplace.domain.model.Discount
import com.android.cabifymarketplace.domain.usecase.DBUseCases
import com.android.cabifymarketplace.domain.usecase.GetDiscountsUseCase
import com.android.cabifymarketplace.presentation.utils.DiscountUtil.discountsCalculate
import com.android.cabifymarketplace.presentation.utils.Formats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val dbUseCases: DBUseCases,
    private val getDiscountsUseCase: GetDiscountsUseCase
) : ViewModel() {

    private val _discounts =
        mutableStateOf<Resource<List<DescriptionCartSummary>>>(Resource.Loading())
    val discounts: State<Resource<List<DescriptionCartSummary>>> = _discounts

    init {
        getDiscountsSummary()
    }

    fun getDiscountsSummary() {
        val listDescriptionCartSummary = mutableListOf<DescriptionCartSummary>()
        viewModelScope.launch(Dispatchers.IO) {
            var totalPrice = BigDecimal(0)
            val order = dbUseCases.getCartUseCase()
            order.forEach {
                listDescriptionCartSummary.add(
                    DescriptionCartSummary(
                        it.name,
                        Formats.currencyFormat(
                            it.getFinalPrice()
                        ),
                        DESCRIPTION_CART_SUMMARY_TYPE_NORMAL,
                        it.quantity
                    )
                )
                totalPrice += it.getFinalPrice()
            }
            getDiscountsUseCase.invoke().onEach {
                when (it) {
                    is Resource.Success<List<Discount>> -> {
                        discountsCalculate(it.data, order).forEach { discount ->
                            listDescriptionCartSummary.add(
                                DescriptionCartSummary(
                                    discount.first,
                                    Formats.currencyFormat(discount.second),
                                    DESCRIPTION_CART_SUMMARY_TYPE_DISCOUNT
                                )
                            )
                            totalPrice -= discount.second
                        }
                        if (listDescriptionCartSummary.isNotEmpty()) {
                            listDescriptionCartSummary.add(
                                DescriptionCartSummary(
                                    "",
                                    Formats.currencyFormat(totalPrice),
                                    DESCRIPTION_CART_SUMMARY_TYPE_TOTAL
                                )
                            )
                        }
                        _discounts.value = Resource.Success(listDescriptionCartSummary.toList())
                    }
                    is Resource.Loading -> {
                        _discounts.value = Resource.Loading()
                    }
                    is Resource.Error -> {
                        _discounts.value = Resource.Error(it.message)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun resetOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            dbUseCases.deleteCartUseCase()
        }
    }
}
