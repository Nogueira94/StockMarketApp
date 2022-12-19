package com.nogueira.stockmarketapp.domain.model

import com.nogueira.stockmarketapp.data.model.StockDetailResponse

data class StockDetail(
    val name: String,
    val symbol: String,
    val desc: String,
    val exchange: String
)

fun StockDetailResponse.toStockDetail() = StockDetail(
    name = Name ?: "",
    symbol = Symbol ?: "",
    desc = Description ?: "",
    exchange = Exchange ?: ""
)