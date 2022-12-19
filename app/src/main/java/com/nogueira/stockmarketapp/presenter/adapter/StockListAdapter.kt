package com.nogueira.stockmarketapp.presenter.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nogueira.designsystem.contract.StockCardContract
import com.nogueira.stockmarketapp.R
import com.nogueira.stockmarketapp.databinding.StockItemBinding

class StockListAdapter(private val stockList: List<StockCardContract>, private val parentFragment: Fragment) : RecyclerView.Adapter<StockListAdapter.StockViewHolder>() {

    private lateinit var navController : NavController

    inner class StockViewHolder (val binding : StockItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val itemView = StockItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        navController = parentFragment.findNavController()
        return StockViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stockList[position]
        holder.binding.stockCard.setStock(stock)
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("STOCK_SYMBOL", stock.symbol)
            navController.navigate(R.id.action_stockListFragment_to_stockDetailFragment,bundle,NavOptions.Builder().setRestoreState(false).build())
        }
    }

    override fun getItemCount(): Int = stockList.size
}