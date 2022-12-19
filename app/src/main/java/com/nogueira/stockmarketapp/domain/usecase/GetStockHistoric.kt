package com.nogueira.stockmarketapp.domain.usecase

import com.nogueira.network.ApiResult
import com.nogueira.stockmarketapp.data.repository.StockRepo
import com.nogueira.stockmarketapp.domain.model.StockHistoric
import com.nogueira.stockmarketapp.domain.model.toStockHistoric
import com.nogueira.stockmarketapp.util.ResultState

class GetStockHistoric(private val stockRepo: StockRepo) : GetStockHistoricUseCase {
    override suspend fun invoke(symbol: String): ResultState<List<StockHistoric>> {
        return when (val response = stockRepo.getStockHistoric(symbol)) {
            is ApiResult.Error -> {
                ResultState.Error(response.statusDesc.toString())
            }
            is ApiResult.Success -> {
                val stockList = response.value.map { it.toStockHistoric() }.sortedBy { it.date }
                ResultState.Success(stockList)
            }
        }
    }
}

interface GetStockHistoricUseCase {
    suspend operator fun invoke(symbol: String): ResultState<List<StockHistoric>>
}