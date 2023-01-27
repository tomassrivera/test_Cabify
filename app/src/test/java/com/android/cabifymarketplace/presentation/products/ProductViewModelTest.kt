package com.android.cabifymarketplace.presentation.products

import com.android.cabifymarketplace.core.Resource
import com.android.cabifymarketplace.domain.model.Products
import com.android.cabifymarketplace.domain.usecase.DBUseCases
import com.android.cabifymarketplace.domain.usecase.GetProductsUseCase
import com.android.cabifymarketplace.utils.MockObjects
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ProductViewModelTest {

    lateinit var productViewModel: ProductViewModel

    @MockK
    lateinit var getProductsUseCase: GetProductsUseCase

    @MockK
    lateinit var dbUseCases: DBUseCases

    @Before
    fun start() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Test viewModel getProducts() list response is correct`() {
        val jsonObj = flow<Resource<Products>> { emit(Resource.Success(MockObjects.getProducts())) }
        every { getProductsUseCase.invoke() } returns jsonObj
        productViewModel = ProductViewModel(getProductsUseCase, dbUseCases)

        productViewModel.getProducts()

        Assert.assertNotNull(productViewModel.productList.value.data)
        Assert.assertEquals(3, productViewModel.productList.value.data?.products?.size)
    }
}
