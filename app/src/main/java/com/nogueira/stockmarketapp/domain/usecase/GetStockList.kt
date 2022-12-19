package com.nogueira.stockmarketapp.domain.usecase

import com.nogueira.network.ApiResult
import com.nogueira.stockmarketapp.data.repository.StockRepo
import com.nogueira.stockmarketapp.domain.model.StockBaseInfo
import com.nogueira.stockmarketapp.domain.model.toStockBaseInfo
import com.nogueira.stockmarketapp.util.ResultState

class GetStockList(private val stockRepo: StockRepo) : GetStockListUseCase {
    override suspend fun invoke(): ResultState<List<StockBaseInfo>> {
        return when (val response = stockRepo.getStockList()) {
            is ApiResult.Error -> {
                ResultState.Error(response.statusDesc.toString())
            }
            is ApiResult.Success -> {
                val stockList = response.value.map { it.toStockBaseInfo() }
                ResultState.Success(stockList)
            }
        }
    }
}

interface GetStockListUseCase {
    suspend operator fun invoke(): ResultState<List<StockBaseInfo>>
}