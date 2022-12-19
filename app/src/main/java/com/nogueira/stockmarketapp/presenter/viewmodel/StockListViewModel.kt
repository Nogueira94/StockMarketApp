package com.nogueira.stockmarketapp.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nogueira.stockmarketapp.domain.model.StockBaseInfo
import com.nogueira.stockmarketapp.domain.usecase.GetStockListUseCase
import com.nogueira.stockmarketapp.util.ResultState
import kotlinx.coroutines.launch

class StockListViewModel(private val getStockListUseCase: GetStockListUseCase) : ViewModel() {

    private val _stockListState = MutableLiveData<ResultState<List<StockBaseInfo>>>()
    val stockListState : LiveData<ResultState<List<StockBaseInfo>>>
        get() = _stockListState

    fun getStockList(){
        viewModelScope.launch {
            getStockListUseCase.invoke().collect{ result ->
                _stockListState.value = result
            }
        }
    }

}