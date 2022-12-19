package com.nogueira.stockmarketapp.data.model

import com.google.gson.annotations.SerializedName

data class StockDetailResponse(
    @SerializedName("200DayMovingAverage")
    val Day200MovingAverage: String?,
    @SerializedName("50DayMovingAverage")
    val Day50MovingAverage: String?,
    @SerializedName("52WeekHigh")
    val Week52High: String?,
    @SerializedName("52WeekLow")
    val Week52Low: String?,
    val Address: String?,
    val AnalystTargetPrice: String?,
    val AssetType: String?,
    val Beta: String?,
    val BookValue: String?,
    val CIK: String?,
    val Country: String?,
    val Currency: String?,
    val Description: String?,
    val DilutedEPSTTM: String?,
    val DividendDate: String?,
    val DividendPerShare: String?,
    val DividendYield: String?,
    val EBITDA: String?,
    val EPS: String?,
    val EVToEBITDA: String?,
    val EVToRevenue: String?,
    val ExDividendDate: String?,
    val Exchange: String?,
    val FiscalYearEnd: String?,
    val ForwardPE: String?,
    val GrossProfitTTM: String?,
    val Industry: String?,
    val LatestQuarter: String?,
    val MarketCapitalization: String?,
    val Name: String?,
    val OperatingMarginTTM: String?,
    val PEGRatio: String?,
    val PERatio: String?,
    val PriceToBookRatio: String?,
    val PriceToSalesRatioTTM: String?,
    val ProfitMargin: String?,
    val QuarterlyEarningsGrowthYOY: String?,
    val QuarterlyRevenueGrowthYOY: String?,
    val ReturnOnAssetsTTM: String?,
    val ReturnOnEquityTTM: String?,
    val RevenuePerShareTTM: String?,
    val RevenueTTM: String?,
    val Sector: String?,
    val SharesOutstanding: String?,
    val Symbol: String?,
    val TrailingPE: String?
)