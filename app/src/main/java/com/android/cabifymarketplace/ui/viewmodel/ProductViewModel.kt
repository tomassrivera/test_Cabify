package com.android.cabifymarketplace.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cabifymarketplace.data.repository.CabifyMarketplaceRepository
import com.android.cabifymarketplace.model.OrderInfo
import com.android.cabifymarketplace.model.Product
import com.android.cabifymarketplace.model.Products
import com.android.cabifymarketplace.ui.common.Resource
import com.android.cabifymarketplace.ui.utils.DiscountUtil.discountsCalculate
import com.android.cabifymarketplace.ui.utils.extensionfunction.changeQuantity

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: CabifyMarketplaceRepository
)  : ViewModel() {

    private val _productList = MutableLiveData<Resource<Products>>()

    fun getProducts(): LiveData<Resource<Products>> {
        viewModelScope.launch(Dispatchers.IO) {
            val products = repository.getProducts()
            _productList.postValue(Resource.Success(products))
        }
        return _productList
    }

    val order: OrderInfo = OrderInfo(mutableListOf())

    fun changeItemQuantity(product: Product, quantity: Int) {
       order.productsCodeSelected.changeQuantity(product, quantity)

    }

    fun getDiscountsSummary(): List<Pair<String, BigDecimal>> {
        return discountsCalculate(repository.getDiscounts(), order.productsCodeSelected)
    }
}