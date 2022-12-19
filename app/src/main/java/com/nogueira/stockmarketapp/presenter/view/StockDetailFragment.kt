package com.nogueira.stockmarketapp.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.nogueira.stockmarketapp.R
import com.nogueira.stockmarketapp.databinding.FragmentStockDetailBinding
import com.nogueira.stockmarketapp.domain.model.StockDetail
import com.nogueira.stockmarketapp.domain.model.StockHistoric
import com.nogueira.stockmarketapp.presenter.viewmodel.StockDetailViewModel
import com.nogueira.stockmarketapp.util.ResultState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters.firstDayOfYear

class StockDetailFragment : Fragment() {

    private lateinit var binding: FragmentStockDetailBinding
    private var stockSymbol: String? = ""

    private val viewModel: StockDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStockDetailBinding.inflate(inflater, container, false)

        stockSymbol = requireArguments().getString("STOCK_SYMBOL")


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stockSymbol?.let {
            viewModel.getStockDetail(it)
            viewModel.getStockHistoric(it)
        }

        setupView()

    }

    private fun setupView() {
        viewModel.stockDetail.observe(viewLifecycleOwner) { state ->
            populateView(state)
        }
        viewModel.stockHistoric.observe(viewLifecycleOwner) { state ->
            populatePricesAndChart(state)
        }
    }

    private fun populatePricesAndChart(state: ResultState<List<StockHistoric>>) {
        when (state) {
            is ResultState.Loading -> {
                binding.chartShimmer.startShimmer()
                binding.textShimmer.startShimmer()
            }
            is ResultState.Success -> {
                if (state.data.isNullOrEmpty()) {
                    //TODO Setup Empty View
                } else {
                    binding.chartShimmer.apply {
                        visibility = GONE
                        stopShimmer()
                    }
                    binding.textShimmer.apply {
                        visibility = GONE
                        stopShimmer()
                    }
                    state.data.let {
                        binding.stockLastClosePrice.apply {
                            visibility = VISIBLE
                            text = resources.getString(
                                R.string.last_value,it[0].closeValue.toString())
                        }
                        binding.stockYtdPrice.apply {
                            visibility = VISIBLE
                            text = resources.getString(
                                R.string.ytd_text,getYTD(it).toString())
                        }
                    }
                    binding.chartHistoric.apply {
                        visibility = VISIBLE
                        setupChart(state.data,parentFragmentManager)
                    }
                }
            }
            is ResultState.Error -> {
                //TODO Setup Error View
            }
        }

    }

    private fun getYTD(stockList: List<StockHistoric>): Double {
        val firstDay = LocalDate.now().with(firstDayOfYear())
        val currentYearHistoric = stockList.filter { it.date >= firstDay }
        return currentYearHistoric.first().closeValue - stockList.last().closeValue
    }

    private fun populateView(state: ResultState<StockDetail>) {
        when (state) {
            is ResultState.Loading -> {
                //TODO Setup Loading View
            }
            is ResultState.Success -> {
                state.data?.let {
                    binding.stockSymbol.text = it.symbol
                    binding.stockName.text = it.name
                    binding.stockDesc.text = it.desc
                }

            }
            is ResultState.Error -> {
                //TODO Setup Error View
            }
        }
    }

}