package com.nogueira.stockmarketapp.data.api

import com.nogueira.stockmarketapp.BuildConfig
import com.nogueira.stockmarketapp.data.model.StockDetailResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val OUTPUT_SIZE_FULL = "full"

interface StockMarketApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getStockList(
        @Query("apikey") apiKey: String = BuildConfig.ALPHA_VANTAGE_API_KEY
    ) : ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getStockDetail(
        @Query("symbol") symbol : String,
        @Query("apikey") apiKey: String = BuildConfig.ALPHA_VANTAGE_API_KEY
    ) : Response<StockDetailResponse>

    @GET("query?function=TIME_SERIES_DAILY_ADJUSTED&datatype=csv")
    suspend fun getStockHistoric(
        @Query("symbol") symbol : String,
        @Query("outputsize") outputsize : String = OUTPUT_SIZE_FULL,
        @Query("apikey") apiKey: String = BuildConfig.ALPHA_VANTAGE_API_KEY
    ) : ResponseBody
}