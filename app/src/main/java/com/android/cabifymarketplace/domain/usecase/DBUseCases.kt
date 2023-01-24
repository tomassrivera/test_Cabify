package com.android.cabifymarketplace.domain.usecase

data class DBUseCases(
    val checkProductExistUseCase: CheckProductExistUseCase,
    val deleteProductCartUseCase: DeleteProductCartUseCase,
    val getCartUseCase: GetCartUseCase,
    val getQuantityProductUseCase: GetQuantityProductUseCase,
    val insertProductCartUseCase: InsertProductCartUseCase,
    val updateProductQuantityUseCase: UpdateProductQuantityUseCase,
    val deleteProductsCartUseCase: DeleteProductsCartUseCase
)
