package com.nogueira.stockmarketapp.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nogueira.stockmarketapp.databinding.FragmentStockListBinding
import com.nogueira.stockmarketapp.domain.model.StockBaseInfo
import com.nogueira.stockmarketapp.presenter.adapter.StockListAdapter
import com.nogueira.stockmarketapp.presenter.viewmodel.StockListViewModel
import com.nogueira.stockmarketapp.util.ResultState
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockListFragment : Fragment() {

    private lateinit var binding: FragmentStockListBinding

    private val viewModel : StockListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStockListBinding.inflate(inflater, container, false)

        setupView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStockList()

        viewModel.stockListState.observe(viewLifecycleOwner){ state ->
            populateView(state)
        }

    }

    private fun populateView(state: ResultState<List<StockBaseInfo>>) {
        when(state){
            is ResultState.Loading ->{
                binding.shimmer.startShimmer()
            }
            is ResultState.Success -> {
                binding.shimmer.visibility = GONE
                binding.rvStocks.visibility = VISIBLE
                binding.shimmer.stopShimmer()
                if(state.data.isNullOrEmpty()){
                    binding.errorContainer.root.visibility = VISIBLE
                } else {
                    binding.rvStocks.adapter = state.data?.let { StockListAdapter(it, this) }
                }
            }
            is ResultState.Error -> {
                binding.errorContainer.root.visibility = VISIBLE
            }
        }
    }

    private fun setupView() {
        binding.rvStocks.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        }
    }


}