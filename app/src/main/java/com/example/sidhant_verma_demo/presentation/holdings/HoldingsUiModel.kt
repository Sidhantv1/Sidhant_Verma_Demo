package com.example.sidhant_verma_demo.presentation.holdings

data class HoldingsUiModel(
    val symbol: String,
    val quantity: String,
    val ltpFormatted: String,
    val pnlFormatted: String,
    val pnlColor: Int
)

