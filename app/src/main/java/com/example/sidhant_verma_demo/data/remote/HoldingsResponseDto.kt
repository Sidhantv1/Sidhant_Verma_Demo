package com.example.sidhant_verma_demo.data.remote

data class HoldingsResponseDto(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)

data class UserHoldingsDto(
    val userHolding: List<HoldingsResponseDto>
)

data class HoldingsApiResponse(
    val data: UserHoldingsDto
)