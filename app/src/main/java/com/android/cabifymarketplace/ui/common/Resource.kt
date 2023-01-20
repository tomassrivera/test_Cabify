package com.android.cabifymarketplace.ui.common

sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    class Failure(val message: String) : Resource<String>()
}