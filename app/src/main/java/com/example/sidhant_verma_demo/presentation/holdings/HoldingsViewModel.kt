package com.example.sidhant_verma_demo.presentation.holdings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sidhant_verma_demo.R
import com.example.sidhant_verma_demo.domain.model.Holding
import com.example.sidhant_verma_demo.domain.usecase.CalculateSummaryUseCase
import com.example.sidhant_verma_demo.domain.usecase.GetHoldingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HoldingsViewModel(
    private val getHoldingsUseCase: GetHoldingsUseCase,
    private val calculateSummaryUseCase: CalculateSummaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HoldingsUiState>(HoldingsUiState.Loading)
    val uiState: StateFlow<HoldingsUiState> = _uiState

    private var isExpanded = false

    init {
        loadHoldings()
    }

    private fun loadHoldings() {
        viewModelScope.launch {
            try {
                val holdings = getHoldingsUseCase()
                val summary = calculateSummaryUseCase(holdings)

                _uiState.value = HoldingsUiState.Success(
                    holdings = holdings.map { it.toUi() },
                    summary = summary,
                    isExpanded = isExpanded
                )

            } catch (e: Exception) {
                _uiState.value = HoldingsUiState.Error("Failed to load data")
            }
        }
    }

    fun toggleSummary() {
        isExpanded = !isExpanded

        val current = _uiState.value
        if (current is HoldingsUiState.Success) {
            _uiState.value = current.copy(isExpanded = isExpanded)
        }
    }

    private fun Holding.toUi(): HoldingsUiModel {
        val pnl = (ltp - avgPrice) * quantity

        return HoldingsUiModel(
            symbol = symbol,
            quantity = quantity.toString(),
            ltpFormatted = "%.2f".format(ltp),
            pnlFormatted = "%.2f".format(pnl),
            pnlColorRes = if (pnl >= 0) {
                R.color.green_shade
            } else {
                R.color.red_shade
            }
        )
    }
}
