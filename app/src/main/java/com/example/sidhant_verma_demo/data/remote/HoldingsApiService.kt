package com.example.sidhant_verma_demo.data.remote

import retrofit2.http.GET

interface HoldingsApiService {
    @GET("/")
    suspend fun getHoldings(): HoldingsResponseDto
}
