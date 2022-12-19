package com.nogueira.stockmarketapp.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nogueira.stockmarketapp.domain.model.StockDetail
import com.nogueira.stockmarketapp.domain.model.StockHistoric
import com.nogueira.stockmarketapp.domain.usecase.GetStockDetailUseCase
import com.nogueira.stockmarketapp.domain.usecase.GetStockHistoricUseCase
import com.nogueira.stockmarketapp.presenter.viewmodel.StockDetailViewModel
import com.nogueira.stockmarketapp.util.ResultState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StockDetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `test when getStockDetail in viewModel is success`() {
        val stockDetail = mockkClass(StockDetail::class)
        val stockHistoric = listOf(mockkClass(StockHistoric::class),mockkClass(StockHistoric::class) )
        val mockGetDetail = mockk<GetStockDetailUseCase>()
        val mockGetHistoric = mockk<GetStockHistoricUseCase>()
        coEvery { mockGetDetail.invoke("") } returns ResultState.Success(stockDetail)
        coEvery { mockGetHistoric.invoke("") } returns ResultState.Success(stockHistoric)
        val vm = StockDetailViewModel(mockGetHistoric,mockGetDetail)

        vm.getStockDetail("")
        vm.getStockHistoric("")

        dispatcher.scheduler.advanceUntilIdle()

        Assert.assertTrue(vm.stockDetail.value is ResultState.Success && vm.stockHistoric.value is ResultState.Success)

        val historicData = vm.stockHistoric.value as ResultState.Success

        assertEquals(2,historicData.data.size)


    }


}