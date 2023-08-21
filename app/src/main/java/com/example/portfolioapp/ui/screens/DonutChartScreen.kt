package com.example.portfolioapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.portfolioapp.ui.vm.DonutChartViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.portfolioapp.data.models.ChartData
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

@Composable
fun DonutChartView(
    chartDataList: List<ChartData>,
    chartColors: List<Int>,
    viewModel: DonutChartViewModel
) {
    val entries = chartDataList.mapIndexed { index, chartData ->
        PieEntry(chartData.percentage, chartData.label)
    }

    val pieDataSet = PieDataSet(entries, null)
    pieDataSet.setDrawValues(true)
    pieDataSet.colors = chartColors

    val pieData = PieData(pieDataSet)
    pieData.setValueFormatter(PercentFormatter())

    pieDataSet.valueFormatter = object : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return "${value.toInt()}%"
        }
    }
    pieDataSet.valueTextSize = 16f

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            text = "My Portfolio",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.TopCenter).padding(50.dp)
        )
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false
                data = pieData
                holeRadius = 50f
                setHoleColor(android.graphics.Color.TRANSPARENT)
                legend.isEnabled = true
                animateY(1400)

                legend.apply {
                    isEnabled = true
                    verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                    horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                    setDrawInside(false)
                    yOffset = 80f
                }

                setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                    override fun onValueSelected(e: Entry?, h: Highlight?) {
                        if (e != null && e is PieEntry) {
                            val selectedChartData = chartDataList.find { it.label == e.label }
                            selectedChartData?.let {
                                viewModel.onChartDataClicked(selectedChartData)
                            }
                        }
                    }

                    override fun onNothingSelected() {

                    }

                })
            }

        }
    )
}

@Composable
fun DonutChartScreen(viewModel: DonutChartViewModel, chartColors: List<Int>) {
    val chartDataList = viewModel.chartDataList
    val selectedChartData = viewModel.selectedChartData.observeAsState()

    if (selectedChartData.value != null) {
        TransactionsScreen(
            chartData = selectedChartData.value!!,
            onBackClicked = { viewModel.clearSelectedChartData() }
        )
    } else {
        DonutChartView(chartDataList, chartColors, viewModel)
    }
}

@Preview
@Composable
fun PreviewDonutChartScreen() {
    val viewModel = DonutChartViewModel()
    val chartColors = listOf(
        android.graphics.Color.parseColor("#9b5de5"),
        android.graphics.Color.parseColor("#f15bb5"),
        android.graphics.Color.parseColor("#00bbf9"),
        android.graphics.Color.parseColor("#ff7d00")
    )

    DonutChartScreen(viewModel, chartColors)
}


