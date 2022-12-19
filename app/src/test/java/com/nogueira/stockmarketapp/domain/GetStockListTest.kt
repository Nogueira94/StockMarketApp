package com.nogueira.stockmarketapp.domain

import com.nogueira.network.ApiResult
import com.nogueira.stockmarketapp.data.model.StockBaseInfoResponse
import com.nogueira.stockmarketapp.data.repository.StockRepo
import com.nogueira.stockmarketapp.domain.usecase.GetStockList
import com.nogueira.stockmarketapp.util.ResultState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetStockListTest {

    val stockList = listOf(
        StockBaseInfoResponse("IBM","IBM","NYSE","1999-10-10"),
        StockBaseInfoResponse("Google","GOOGL","NASDAQ","2005-03-04")
    )

    @Test
    fun `teste domain layer when GetStockList() success`() = runTest {
        val mockRepo = mockk<StockRepo> {
            coEvery { getStockList() } returns ApiResult.Success(stockList)
        }

        val result = GetStockList(mockRepo).invoke()

        Assert.assertTrue(result is ResultState.Success)

        val dataLoaded = result as ResultState.Success

        Assert.assertEquals(2,dataLoaded.data.size)

    }

    @Test
    fun `teste domain layer when GetStockList() fail`() = runTest {
        val mockRepo = mockk<StockRepo> {
            coEvery { getStockList() } returns ApiResult.Error(404,"Test exception")
        }

        val result = GetStockList(mockRepo).invoke()

        Assert.assertTrue(result is ResultState.Error)

    }

}