package com.example.sidhant_verma_demo

import com.example.sidhant_verma_demo.domain.usecase.CalculateSummaryUseCase
import com.example.sidhant_verma_demo.domain.model.Holding
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateSummaryUseCaseTest {

    private val useCase = CalculateSummaryUseCase()

    @Test
    fun calculatesCorrectSummaryForValidHoldings() {
        val holdings = listOf(
            Holding("ABC", 10, 100.0, 80.0, 110.0),
            Holding("XYZ", 5, 200.0, 180.0, 190.0)
        )

        val result = useCase(holdings)

        assertEquals(10 * 100.0 + 5 * 200.0, result.currentValue, 0.01)
        assertEquals(10 * 80.0 + 5 * 180.0, result.totalInvestment, 0.01)
        assertEquals(
            result.currentValue - result.totalInvestment,
            result.totalPnL,
            0.01
        )
        assertEquals(
            ((110.0 - 100.0) * 10) + ((190.0 - 200.0) * 5),
            result.todayPnL,
            0.01
        )
    }

    @Test
    fun returnsZeroValuesWhenListIsEmpty() {
        val result = useCase(emptyList())

        assertEquals(0.0, result.currentValue, 0.01)
        assertEquals(0.0, result.totalInvestment, 0.01)
        assertEquals(0.0, result.totalPnL, 0.01)
        assertEquals(0.0, result.todayPnL, 0.01)
    }

    @Test
    fun handlesNegativePnLScenariosCorrectly() {
        val holdings = listOf(
            Holding("TEST", 10, 80.0, 100.0, 70.0)
        )

        val result = useCase(holdings)

        assertEquals(10 * 80.0, result.currentValue, 0.01)
        assertEquals(10 * 100.0, result.totalInvestment, 0.01)
        assertEquals(-200.0, result.totalPnL, 0.01)
        assertEquals((70.0 - 80.0) * 10, result.todayPnL, 0.01)
    }
}
