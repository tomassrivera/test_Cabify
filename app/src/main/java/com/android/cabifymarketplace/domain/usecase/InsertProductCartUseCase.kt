package com.android.cabifymarketplace.domain.usecase

import com.android.cabifymarketplace.domain.model.db.ProductOrder
import com.android.cabifymarketplace.domain.repository.CabifyMarketplaceRepository
import javax.inject.Inject

class InsertProductCartUseCase @Inject constructor(
    private val repository: CabifyMarketplaceRepository
) {
    suspend operator fun invoke(product: ProductOrder) {
        repository.insertProduct(product)
    }
}
