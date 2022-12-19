package com.nogueira.stockmarketapp.domain.usecase

import com.nogueira.network.ApiResult
import com.nogueira.stockmarketapp.data.repository.StockRepo
import com.nogueira.stockmarketapp.domain.model.StockHistoric
import com.nogueira.stockmarketapp.domain.model.toStockHistoric
import com.nogueira.stockmarketapp.util.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetStockHistoric(private val stockRepo: StockRepo) : GetStockHistoricUseCase {
    override suspend fun invoke(symbol: String): Flow<ResultState<List<StockHistoric>>> {
        return flow {
            emit(ResultState.Loading(true))
            when (val response = stockRepo.getStockHistoric(symbol)) {
                is ApiResult.Error -> {
                    emit(ResultState.Error(response.statusDesc.toString()))
                }
                is ApiResult.Success -> {
                    val stockList = response.value.map { it.toStockHistoric() }.sortedBy { it.date }
                    emit(ResultState.Success(stockList))
                }
            }

        }
    }
}

interface GetStockHistoricUseCase{
    suspend operator fun invoke(symbol: String): Flow<ResultState<List<StockHistoric>>>
}