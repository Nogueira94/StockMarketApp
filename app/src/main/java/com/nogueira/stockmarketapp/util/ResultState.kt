package com.nogueira.stockmarketapp.util

sealed class ResultState<out T> {
    class Success<out T>(val data: T) : ResultState<T>()
    class Error(errorMessage: String) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
}