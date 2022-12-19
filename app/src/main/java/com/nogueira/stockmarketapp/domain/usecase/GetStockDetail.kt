package com.nogueira.stockmarketapp.domain.usecase

import com.nogueira.network.ApiResult
import com.nogueira.stockmarketapp.data.repository.StockRepo
import com.nogueira.stockmarketapp.domain.model.StockDetail
import com.nogueira.stockmarketapp.domain.model.toStockDetail
import com.nogueira.stockmarketapp.util.ResultState

class GetStockDetail(private val stockRepo: StockRepo) : GetStockDetailUseCase {
    override suspend fun invoke(symbol: String): ResultState<StockDetail> {
        return when (val response = stockRepo.getStockDetail(symbol)) {
            is ApiResult.Error -> {
                ResultState.Error(response.statusDesc.toString())
            }
            is ApiResult.Success -> {
                ResultState.Success(response.value.toStockDetail())
            }
        }
    }
}


interface GetStockDetailUseCase {
    suspend operator fun invoke(symbol: String): ResultState<StockDetail>
}