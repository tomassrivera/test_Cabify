package com.android.cabifymarketplace.domain.usecase

import androidx.lifecycle.LiveData
import com.android.cabifymarketplace.domain.model.db.ProductOrder
import com.android.cabifymarketplace.domain.repository.CabifyMarketplaceRepository
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val repository: CabifyMarketplaceRepository
) {
    fun invokeLiveData(): LiveData<List<ProductOrder>> {
        return repository.getProductsCart()
    }

    suspend operator fun invoke(): List<ProductOrder> {
        return repository.getOrder()
    }
}
