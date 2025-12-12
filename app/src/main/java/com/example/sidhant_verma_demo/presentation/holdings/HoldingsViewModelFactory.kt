package com.example.sidhant_verma_demo.presentation.holdings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sidhant_verma_demo.domain.usecase.CalculateSummaryUseCase
import com.example.sidhant_verma_demo.domain.usecase.GetHoldingsUseCase

class HoldingsViewModelFactory(
    private val getHoldingsUseCase: GetHoldingsUseCase,
    private val calculateSummaryUseCase: CalculateSummaryUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HoldingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HoldingsViewModel(
                getHoldingsUseCase,
                calculateSummaryUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}

