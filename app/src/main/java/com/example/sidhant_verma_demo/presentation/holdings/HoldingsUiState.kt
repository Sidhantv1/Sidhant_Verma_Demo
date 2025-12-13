package com.example.sidhant_verma_demo.presentation.holdings

import com.example.sidhant_verma_demo.domain.model.PortfolioSummary

sealed class HoldingsUiState {
    object Loading : HoldingsUiState()
    data class Error(val message: String) : HoldingsUiState()
    data class Success(
        val holdings: List<HoldingsUiModel>,
        val summary: PortfolioSummary,
        val isExpanded: Boolean
    ) : HoldingsUiState()
}

