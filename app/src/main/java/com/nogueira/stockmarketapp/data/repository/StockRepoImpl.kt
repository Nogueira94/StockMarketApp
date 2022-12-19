package com.nogueira.stockmarketapp.data.repository

import com.nogueira.network.ApiResult
import com.nogueira.network.parseResult
import com.nogueira.stockmarketapp.data.api.StockMarketApi
import com.nogueira.stockmarketapp.data.model.DailyInfoResponse
import com.nogueira.stockmarketapp.data.model.StockBaseInfoResponse
import com.nogueira.stockmarketapp.data.model.StockDetailResponse
import com.nogueira.stockmarketapp.data.util.CsvParserHelper

class StockRepoImpl(
    private val service : StockMarketApi
) : StockRepo {
    override suspend fun getStockList(): ApiResult<List<StockBaseInfoResponse>> {
            val stockList = try {
                val response = service.getStockList()
                CsvParserHelper().parseStockList(response.byteStream())
            } catch (e: Exception){
                e.printStackTrace()
                return ApiResult.Error(null,e.message.toString())
            }
        stockList.let {
            return ApiResult.Success(it)
        }
    }

    override suspend fun getStockDetail(symbol: String): ApiResult<StockDetailResponse> {
        return service.getStockDetail(symbol).parseResult()
    }

    override suspend fun getStockHistoric(symbol: String): ApiResult<List<DailyInfoResponse>> {
        val stockHistoric = try{
            val response = service.getStockHistoric(symbol)
            CsvParserHelper().parseHistoricList(response.byteStream())
        } catch (e: Exception){
            e.printStackTrace()
            return ApiResult.Error(null,e.message.toString())
        }
        stockHistoric.let {
            return ApiResult.Success(it)
        }
    }
}

interface StockRepo {
    suspend fun getStockList() : ApiResult<List<StockBaseInfoResponse>>
    suspend fun getStockHistoric(symbol : String) : ApiResult<List<DailyInfoResponse>>
    suspend fun getStockDetail(symbol : String) : ApiResult<StockDetailResponse>
}