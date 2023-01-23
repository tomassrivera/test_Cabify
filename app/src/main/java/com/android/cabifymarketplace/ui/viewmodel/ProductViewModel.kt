package com.android.cabifymarketplace.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cabifymarketplace.data.repository.CabifyMarketplaceRepository
import com.android.cabifymarketplace.model.Product
import com.android.cabifymarketplace.model.Products
import com.android.cabifymarketplace.model.db.ProductOrder
import com.android.cabifymarketplace.ui.common.Resource
import com.android.cabifymarketplace.ui.utils.DiscountUtil.discountsCalculate

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    val order: LiveData<List<ProductOrder>> by lazy {
        repository.getProductsCart()
    }

    fun changeItemQuantity(product: Product, quantity: Int) {
        if (quantity > 0) {
            addProduct(product)
        } else {
            removeProduct(product)
        }
    }


    fun addProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.checkProductExists(product.code)) {
                repository.updateQuantity(product.code, 1)
            } else {
                repository.insertProduct(product.convertToProductOrder())
            }
        }
    }

    fun removeProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.getQuantityByProductCode(product.code) > 1) {
                repository.updateQuantity(product.code, -1)
            } else {
                repository.deleteProduct(product.code)
            }
        }
    }

    fun getDiscountsSummary(): List<Pair<String, BigDecimal>> {
        return discountsCalculate(repository.getDiscounts(), order.value)
    }

    fun getTotalPriceToPay(): BigDecimal {
        return order.value?.sumOf { it.price * it.quantity }?.toBigDecimal()?. let { sumTotalPrice ->
            sumTotalPrice.subtract(getDiscountsSummary().sumOf { x -> x.second})
        } ?: BigDecimal(0)
    }

    fun resetOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProducts()
        }
    }
}