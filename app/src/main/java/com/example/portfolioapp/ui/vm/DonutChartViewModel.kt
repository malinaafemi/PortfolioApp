package com.example.portfolioapp.ui.vm

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.portfolioapp.data.models.ChartData
import com.example.portfolioapp.data.models.Transaction

class DonutChartViewModel : ViewModel() {

    private val rawChartData: List<ChartData> = listOf(
        ChartData(
            label = "Tarik Tunai",
            percentage = 55f,
            data = listOf(
                Transaction("21/01/2023", 1000000),
                Transaction("20/01/2023", 500000),
                Transaction("19/01/2023", 1000000)
            )
        ),
        ChartData(
            label = "QRIS Payment",
            percentage = 31f,
            data = listOf(
                Transaction("21/01/2023", 159000),
                Transaction("20/01/2023", 35000),
                Transaction("19/01/2023", 1500)
            )
        ),
        ChartData(
            label = "Topup Gopay",
            percentage = 7.7f,
            data = listOf(
                Transaction("21/01/2023", 200000),
                Transaction("20/01/2023", 195000),
                Transaction("19/01/2023", 5000000)
            )
        ),
        ChartData(
            label = "Lainnya",
            percentage = 6.3f,
            data = listOf(
                Transaction("21/01/2023", 1000000),
                Transaction("20/01/2023", 500000),
                Transaction("19/01/2023", 1000000)
            )
        )
    )

    val chartDataList: List<ChartData> = rawChartData

    private val _selectedChartData = MutableLiveData<ChartData?>()
    val selectedChartData: LiveData<ChartData?> = _selectedChartData

    fun onChartDataClicked(chartData: ChartData) {
        _selectedChartData.value = chartData
    }

    fun clearSelectedChartData() {
        _selectedChartData.value = null
    }

}