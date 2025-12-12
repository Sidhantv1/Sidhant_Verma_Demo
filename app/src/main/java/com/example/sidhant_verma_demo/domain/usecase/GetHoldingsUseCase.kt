package com.example.sidhant_verma_demo.domain.usecase

import com.example.sidhant_verma_demo.domain.HoldingsRepository
import com.example.sidhant_verma_demo.domain.model.Holding

class GetHoldingsUseCase(private val repository: HoldingsRepository) {
    suspend operator fun invoke(): List<Holding> {
        return repository.getHoldings()
    }
}
