package com.nogueira.stockmarketapp

import com.nogueira.network.ServiceNetwork
import com.nogueira.stockmarketapp.data.api.StockMarketApi
import com.nogueira.stockmarketapp.data.repository.StockRepo
import com.nogueira.stockmarketapp.data.repository.StockRepoImpl
import com.nogueira.stockmarketapp.domain.usecase.*
import com.nogueira.stockmarketapp.presenter.viewmodel.StockDetailViewModel
import com.nogueira.stockmarketapp.presenter.viewmodel.StockListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<StockRepo> { StockRepoImpl(get()) }

    single<GetStockListUseCase> { GetStockList(get()) }
    single<GetStockDetailUseCase> { GetStockDetail(get()) }
    single<GetStockHistoricUseCase> { GetStockHistoric(get()) }

    viewModel  { StockListViewModel(get()) }

    viewModel  { StockDetailViewModel(get(),get()) }
}

val networkModule = module {
    single { ServiceNetwork().createService(StockMarketApi::class.java) }
}