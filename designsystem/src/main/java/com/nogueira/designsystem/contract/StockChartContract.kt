package com.nogueira.designsystem.contract

import java.time.LocalDate

interface StockChartContract {
    val date : LocalDate
    val closeValue : Double
}