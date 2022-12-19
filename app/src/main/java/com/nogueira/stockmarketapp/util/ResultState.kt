package com.nogueira.stockmarketapp.util

sealed class ResultState<T>(val data: T? = null, val errorMessage: String? = null) {
    class Success<T>(data: T) : ResultState<T>(data)
    class Error<T>(errorMessage: String,data: T? = null) : ResultState<T>(data,errorMessage)
    class Loading<T>(val isLoading : Boolean = true) : ResultState<T>(null)
}