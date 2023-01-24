package com.android.cabifymarketplace.presentation.products

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cabifymarketplace.core.Resource
import com.android.cabifymarketplace.domain.model.Product
import com.android.cabifymarketplace.domain.model.Products
import com.android.cabifymarketplace.domain.model.db.ProductOrder
import com.android.cabifymarketplace.domain.usecase.DBUseCases
import com.android.cabifymarketplace.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val dbUseCases: DBUseCases
) : ViewModel() {

    private val _productList = mutableStateOf<Resource<Products>>(Resource.Loading())
    val productList: State<Resource<Products>> = _productList

    init {
        getProducts()
    }

    val order: LiveData<List<ProductOrder>> get() = _order
    private val _order: LiveData<List<ProductOrder>> by lazy {
        dbUseCases.getCartUseCase.invokeLiveData()
    }

    private fun getProducts() {
        getProductsUseCase().onEach {
            _productList.value = it
        }.launchIn(viewModelScope)
    }

    fun changeItemQuantity(product: Product, quantity: Int) {
        if (quantity > 0) {
            addProduct(product)
        } else {
            removeProduct(product)
        }
    }

    private fun addProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            if (dbUseCases.checkProductExistUseCase(product.code)) {
                dbUseCases.updateProductQuantityUseCase(product.code, 1)
            } else {
                dbUseCases.insertProductCartUseCase(product.convertToProductOrder())
            }
        }
    }

    private fun removeProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            if (dbUseCases.getQuantityProductUseCase(product.code) > 1) {
                dbUseCases.updateProductQuantityUseCase(product.code, -1)
            } else {
                dbUseCases.deleteProductCartUseCase(product.code)
            }
        }
    }
}
