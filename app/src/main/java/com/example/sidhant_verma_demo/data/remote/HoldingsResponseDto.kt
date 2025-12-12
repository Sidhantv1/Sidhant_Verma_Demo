package com.example.sidhant_verma_demo.data.remote

data class HoldingsResponseDto(
    val data: List<HoldingDto>
)

data class HoldingDto(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)
