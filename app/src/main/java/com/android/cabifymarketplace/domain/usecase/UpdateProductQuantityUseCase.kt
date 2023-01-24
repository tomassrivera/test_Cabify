package com.android.cabifymarketplace.domain.usecase

import com.android.cabifymarketplace.domain.repository.CabifyMarketplaceRepository
import javax.inject.Inject

class UpdateProductQuantityUseCase @Inject constructor(
    private val repository: CabifyMarketplaceRepository
) {
    suspend operator fun invoke(code: String, quantity: Int) {
        repository.updateQuantity(code, quantity)
    }
}
