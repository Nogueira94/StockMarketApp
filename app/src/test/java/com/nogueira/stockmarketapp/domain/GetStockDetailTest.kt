package com.nogueira.stockmarketapp.domain

import com.nogueira.network.ApiResult
import com.nogueira.stockmarketapp.data.model.StockDetailResponse
import com.nogueira.stockmarketapp.data.repository.StockRepo
import com.nogueira.stockmarketapp.domain.usecase.GetStockDetail
import com.nogueira.stockmarketapp.util.ResultState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetStockDetailTest {

    val stockDetail = StockDetailResponse(
        "",
        "",
        "",
        "",
        Symbol = "IBM",
        AssetType = "Common Stock",
        Name = "International Business Machines",
        Description = "",
        CIK = "51143",
        Exchange = "NYSE",
        Currency = "USD",
        Country = "USA",
        Sector = "TECHNOLOGY",
        Industry = "COMPUTER & OFFICE EQUIPMENT",
        Address = "1 NEW ORCHARD ROAD, ARMONK, NY, US",
        FiscalYearEnd = "December",
        LatestQuarter = "2022-09-30",
        MarketCapitalization = "125628211000",
        EBITDA = "12010000000",
        PERatio = "23.02",
        PEGRatio = "1.276",
        BookValue = "22.2",
        DividendPerShare = "6.59",
        DividendYield = "0.0471",
        EPS = "6.09",
        RevenuePerShareTTM = "67.2",
        ProfitMargin = "0.0209",
        OperatingMarginTTM = "0.115",
        ReturnOnAssetsTTM = "0.0322",
        ReturnOnEquityTTM = "0.0648",
        RevenueTTM = "60535001000",
        GrossProfitTTM = "31486000000",
        DilutedEPSTTM = "6.09",
        QuarterlyEarningsGrowthYOY = "0.041",
        QuarterlyRevenueGrowthYOY = "0.065",
        AnalystTargetPrice = "141.66",
        TrailingPE = "23.02",
        ForwardPE = "15.55",
        PriceToSalesRatioTTM = "2.108",
        PriceToBookRatio = "6.75",
        EVToRevenue = "2.969",
        EVToEBITDA = "25.81",
        Beta = "0.889",
        SharesOutstanding = "896320000",
        DividendDate = "2022-12-10",
        ExDividendDate = "2022-11-09"
    )

    @Test
    fun `teste domain layer when GetStockDetail() success`() = runTest {
        val mockRepo = mockk<StockRepo> {
            coEvery { getStockDetail(stockDetail.Symbol!!) } returns ApiResult.Success(stockDetail)
        }

        val result = GetStockDetail(mockRepo).invoke(stockDetail.Symbol!!)

        assertTrue(result is ResultState.Success)

        val dataLoaded = result as ResultState.Success

        assertEquals(stockDetail.Symbol,dataLoaded.data.symbol)

    }

    @Test
    fun `teste domain layer when GetStockDetail() fail`() = runTest {
        val mockRepo = mockk<StockRepo> {
            coEvery { getStockDetail(stockDetail.Symbol!!) } returns ApiResult.Error(404,"Test exception")
        }

        val result = GetStockDetail(mockRepo).invoke(stockDetail.Symbol!!)

        assertTrue(result is ResultState.Error)

    }

}