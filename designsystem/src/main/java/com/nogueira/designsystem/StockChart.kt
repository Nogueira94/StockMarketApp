package com.nogueira.designsystem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.nogueira.designsystem.contract.StockChartContract
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*


class StockChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val chart: LineChart
    private val changeDateRange: TextView
    private var originalList = emptyList<StockChartContract>()
    private var fragManager : FragmentManager? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.stock_chart_layout,this,true)
        chart = findViewById(R.id.historic_chart)
        changeDateRange = findViewById(R.id.change_date)
    }

    fun setupChart(values : List<StockChartContract>,fragmentManager: FragmentManager){
        if(originalList.isEmpty()) originalList = values
        fragManager = fragmentManager

        fillChart(values)

        changeDateRange.visibility = VISIBLE

    }

    private fun fillChart(values: List<StockChartContract>) {
        val valuesTochart = getElements(values)
        val xvalues = valuesTochart.sortedBy { it.date }.map { it.date.toString() }
        val data : MutableList<Entry> = mutableListOf()
        valuesTochart.forEachIndexed { index, value ->
            data.add(Entry(index.toFloat(),value.closeValue.toFloat()))
        }

        val lineDataSet = LineDataSet(data,"HISTORIC")
        val chartData = LineData(styleLineDataSet(lineDataSet))

        val xAxis = chart.xAxis
        val axisRight = chart.axisRight

        axisRight.isEnabled = false

        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            axisMinimum = 0f
            axisMaximum = 4f
            setLabelCount(5,true)
            setDrawLimitLinesBehindData(true)
            labelRotationAngle = 300F
            valueFormatter = AxisXFormat(xvalues)
        }

        chart.description = null
        chart.legend.isEnabled = false
        chart.setPinchZoom(false)
        chart.setTouchEnabled(false)


        chart.data = chartData
        chart.animateXY(1500,1500)

        changeDateRange.setOnClickListener { openDataRagePicker(values.first().date){ it ->
            val startDate = it.first
            val endDate = it.second

            val indexStart = originalList.indexOfFirst { it.date >= startDate }
            val indexFinal = originalList.indexOfLast { it.date <= endDate }

            fillChart(originalList.slice(indexStart..indexFinal))


        } }

    }

    private fun openDataRagePicker(firstDate: LocalDate, updateChart : (Pair<LocalDate,LocalDate>) -> Unit) {
        val defaultZoneId = ZoneId.systemDefault()
        val date = Date.from(firstDate.atStartOfDay(defaultZoneId).toInstant());
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    androidx.core.util.Pair(
                        date.time,
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()


        fragManager?.let { dateRangePicker.show(it,"SHOW DATE RANGE PICKER") }
        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val startDate  = Instant.ofEpochMilli(selection.first).atZone(ZoneId.systemDefault())
            val endDate  = Instant.ofEpochMilli(selection.second).atZone(ZoneId.systemDefault())
            if(startDate.until(endDate,ChronoUnit.DAYS) >= 6) {
                updateChart(Pair(startDate.toLocalDate(), endDate.toLocalDate()))
            } else {
                Toast.makeText(context, "Please select a range higher than 5 days", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getElements(data: List<StockChartContract>): List<StockChartContract> {
        val first = data.first()
        val last = data.last()
        val mid = data[data.size / 2]
        val lowMid = data.filter { it.date <= mid.date }
        val upMid = data.filter { it.date > mid.date }
        return arrayListOf(first,last,mid,lowMid[lowMid.size / 2],upMid[upMid.size / 2]).sortedBy { it.date }
    }


    private fun styleLineDataSet(lineDataSet: LineDataSet) = lineDataSet.apply {
        valueTextSize = resources.getDimension(R.dimen.chart_info_value)
        lineWidth = 3f
        isHighlightEnabled = true
        setDrawHighlightIndicators(false)
        setDrawCircles(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER
        setDrawFilled(true)
    }

    private inner class AxisXFormat(private val values: List<String>) : ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            return value.toString()
        }

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return if(value.toInt() >= 0  && value.toInt() <= values.size - 1) {
                values[value.toInt()]
            } else {
                ("").toString()
            }
        }
    }

}
