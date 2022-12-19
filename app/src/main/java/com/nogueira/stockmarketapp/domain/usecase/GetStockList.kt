package com.nogueira.stockmarketapp.domain.usecase

import com.nogueira.network.ApiResult
import com.nogueira.stockmarketapp.data.repository.StockRepo
import com.nogueira.stockmarketapp.domain.model.StockBaseInfo
import com.nogueira.stockmarketapp.domain.model.toStockBaseInfo
import com.nogueira.stockmarketapp.util.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetStockList(private val stockRepo: StockRepo) : GetStockListUseCase {
    override suspend fun invoke(): Flow<ResultState<List<StockBaseInfo>>> {
        return flow {
            emit(ResultState.Loading(true))
            when (val response = stockRepo.getStockList()) {
                is ApiResult.Error -> {
                    emit(ResultState.Error(response.statusDesc.toString()))
                }
                is ApiResult.Success -> {
                    val stockList = response.value.map { it.toStockBaseInfo() }
                    emit(ResultState.Success(stockList))
                }
            }
        }
    }

}

interface GetStockListUseCase {
    suspend operator fun invoke(): Flow<ResultState<List<StockBaseInfo>>>
}