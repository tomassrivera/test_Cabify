package com.android.cabifymarketplace.domain.usecase

import com.android.cabifymarketplace.core.Resource
import com.android.cabifymarketplace.domain.model.Discount
import com.android.cabifymarketplace.domain.repository.CabifyMarketplaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDiscountsUseCase @Inject constructor(
    private val repository: CabifyMarketplaceRepository
) {
    operator fun invoke(): Flow<Resource<List<Discount>>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getDiscounts()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}