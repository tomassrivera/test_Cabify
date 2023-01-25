package com.android.cabifymarketplace.domain.usecase

data class DBUseCases(
    val checkProductExistUseCase: CheckProductExistUseCase,
    val deleteProductCartUseCase: DeleteProductCartUseCase,
    val getCartUseCase: GetCartUseCase,
    val getProductQuantityUseCase: GetProductQuantityUseCase,
    val insertProductCartUseCase: InsertProductCartUseCase,
    val updateProductQuantityUseCase: UpdateProductQuantityUseCase,
    val deleteCartUseCase: DeleteCartUseCase
)
