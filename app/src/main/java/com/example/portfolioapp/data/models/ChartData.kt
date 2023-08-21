package com.example.portfolioapp.data.models

data class ChartData(
    val label: String,
    val percentage: Float,
    val data: List<Transaction>
)
