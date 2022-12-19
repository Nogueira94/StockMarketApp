package com.nogueira.stockmarketapp.domain.model

import com.nogueira.designsystem.contract.StockCardContract
import com.nogueira.stockmarketapp.data.model.StockBaseInfoResponse
import java.time.LocalDate

data class StockBaseInfo(
    override val name: String,
    override val symbol: String,
    override val exchange: String,
    override val ipoDate: LocalDate,
) : StockCardContract

fun StockBaseInfoResponse.toStockBaseInfo() = StockBaseInfo(
    name = name ?: "",
    symbol = symbol?: "",
    exchange = exchange?: "",
    ipoDate = LocalDate.parse(ipoDate)
)