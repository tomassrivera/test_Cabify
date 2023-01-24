package com.android.cabifymarketplace.domain.usecase

import com.android.cabifymarketplace.domain.repository.CabifyMarketplaceRepository
import javax.inject.Inject

class GetQuantityProductUseCase @Inject constructor(
    private val repository: CabifyMarketplaceRepository
) {
    suspend operator fun invoke(code: String): Int {
        return repository.getQuantityByProductCode(code)
    }
}
