package com.example.sidhant_verma_demo.domain

import com.example.sidhant_verma_demo.domain.model.Holding

interface HoldingsRepository {
    suspend fun getHoldings(): List<Holding>
}
