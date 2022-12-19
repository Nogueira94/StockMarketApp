package com.nogueira.designsystem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nogueira.designsystem.contract.StockCardContract

class StockItemCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val name: TextView
    private val symbol: TextView
    private val exchange: TextView
    private val ipoDate: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.stock_item_card,this,true)

        name = findViewById(R.id.stock_name)
        symbol = findViewById(R.id.stock_symbol)
        exchange = findViewById(R.id.stock_exchange)
        ipoDate = findViewById(R.id.stock_ipo_date)

    }

    fun setStock(stock : StockCardContract){
        name.text = stock.name
        symbol.text = stock.symbol
        exchange.text = stock.exchange
        ipoDate.text = stock.ipoDate.toString()
    }


}