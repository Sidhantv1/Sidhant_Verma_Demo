package com.example.sidhant_verma_demo.domain.usecase

import com.example.sidhant_verma_demo.domain.HoldingsRepository
import com.example.sidhant_verma_demo.domain.model.Holding

open class GetHoldingsUseCase(private val repository: HoldingsRepository?) {
    open suspend operator fun invoke(): List<Holding> {
        return repository?.getHoldings() ?: emptyList()
    }
}
