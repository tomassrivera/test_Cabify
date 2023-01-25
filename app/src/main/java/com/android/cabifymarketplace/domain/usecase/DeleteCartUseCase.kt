package com.android.cabifymarketplace.domain.usecase

import com.android.cabifymarketplace.domain.repository.CabifyMarketplaceRepository
import javax.inject.Inject

class DeleteCartUseCase @Inject constructor(
    private val repository: CabifyMarketplaceRepository
) {
    suspend operator fun invoke() {
        repository.deleteProducts()
    }
}
