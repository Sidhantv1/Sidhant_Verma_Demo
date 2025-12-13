package com.example.sidhant_verma_demo.domain.usecase

import com.example.sidhant_verma_demo.domain.model.Holding
import com.example.sidhant_verma_demo.domain.model.PortfolioSummary

class CalculateSummaryUseCase {

    operator fun invoke(holdings: List<Holding>): PortfolioSummary {

        val currentValue = holdings.sumOf { it.ltp * it.quantity }

        val totalInvestment = holdings.sumOf { it.avgPrice * it.quantity }

        val totalPnL = currentValue - totalInvestment

        val todayPnL = holdings.sumOf { (it.close - it.ltp) * it.quantity }

        return PortfolioSummary(
            currentValue = currentValue,
            totalInvestment = totalInvestment,
            totalPnL = totalPnL,
            todayPnL = todayPnL
        )
    }
}
