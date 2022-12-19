package com.nogueira.stockmarketapp.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nogueira.stockmarketapp.domain.model.StockDetail
import com.nogueira.stockmarketapp.domain.model.StockHistoric
import com.nogueira.stockmarketapp.domain.usecase.GetStockDetailUseCase
import com.nogueira.stockmarketapp.domain.usecase.GetStockHistoricUseCase
import com.nogueira.stockmarketapp.util.ResultState
import kotlinx.coroutines.launch

class StockDetailViewModel(
    private val getStockHistoricUseCase: GetStockHistoricUseCase,
    private val getStockDetailUseCase: GetStockDetailUseCase
) : ViewModel() {

    private val _stockDetail = MutableLiveData<ResultState<StockDetail>>()
    val stockDetail : LiveData<ResultState<StockDetail>>
        get() = _stockDetail

    private val _stockHistoric = MutableLiveData<ResultState<List<StockHistoric>>>()
    val stockHistoric : LiveData<ResultState<List<StockHistoric>>>
        get() = _stockHistoric

    fun getStockDetail(symbol : String){
        viewModelScope.launch {
            _stockDetail.value = ResultState.Loading
            _stockDetail.value = getStockDetailUseCase.invoke(symbol)
        }
    }

    fun getStockHistoric(symbol: String) {
        viewModelScope.launch {
            _stockHistoric.value = ResultState.Loading
            _stockHistoric.value = getStockHistoricUseCase.invoke(symbol)
        }
    }

}