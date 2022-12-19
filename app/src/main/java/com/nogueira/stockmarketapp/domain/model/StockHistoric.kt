package com.nogueira.stockmarketapp.domain.model

import com.nogueira.designsystem.contract.StockChartContract
import com.nogueira.stockmarketapp.data.model.DailyInfoResponse
import java.time.LocalDate

data class StockHistoric(
    override val date : LocalDate,
    override val closeValue : Double,
) : StockChartContract

fun DailyInfoResponse.toStockHistoric() = StockHistoric(
    date = LocalDate.parse(date),
    closeValue = closeValue?.toDouble() ?: 0.0
)