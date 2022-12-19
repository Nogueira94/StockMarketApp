package com.nogueira.designsystem.contract

import java.time.LocalDate

interface StockCardContract {
    val name: String
    val symbol: String
    val exchange: String
    val ipoDate: LocalDate
}