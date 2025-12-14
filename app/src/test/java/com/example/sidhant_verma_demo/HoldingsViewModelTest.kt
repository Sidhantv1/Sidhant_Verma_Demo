package com.example.sidhant_verma_demo

import com.example.sidhant_verma_demo.domain.HoldingsRepository
import com.example.sidhant_verma_demo.domain.model.Holding
import com.example.sidhant_verma_demo.domain.usecase.CalculateSummaryUseCase
import com.example.sidhant_verma_demo.domain.usecase.GetHoldingsUseCase
import com.example.sidhant_verma_demo.presentation.holdings.HoldingsUiState
import com.example.sidhant_verma_demo.presentation.holdings.HoldingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private val sampleHoldings = listOf(
        Holding("ABC", 10, 100.0, 80.0, 110.0)
    )

    // Fake Repository Success
    class FakeSuccessRepository : HoldingsRepository {
        override suspend fun getHoldings(): List<Holding> = listOf(
            Holding("ABC", 10, 100.0, 80.0, 110.0)
        )
    }

    // Fake Repository Error
    class FakeErrorRepository : HoldingsRepository {
        override suspend fun getHoldings(): List<Holding> {
            throw Exception("Network error")
        }
    }

    @Test
    fun viewModelEmitsSuccessStateWhenDataLoads() = runTest {
        val vm = HoldingsViewModel(
            getHoldingsUseCase = GetHoldingsUseCase(FakeSuccessRepository()),
            calculateSummaryUseCase = CalculateSummaryUseCase()
        )

        advanceUntilIdle()

        assertTrue(vm.uiState.value is HoldingsUiState.Success)
    }

    @Test
    fun viewModelEmitsErrorStateWhenRepositoryThrows() = runTest {
        val vm = HoldingsViewModel(
            getHoldingsUseCase = GetHoldingsUseCase(FakeErrorRepository()),
            calculateSummaryUseCase = CalculateSummaryUseCase()
        )

        advanceUntilIdle()

        assertTrue(vm.uiState.value is HoldingsUiState.Error)
    }

    @Test
    fun toggleSummaryChangesExpandedValueInSuccessState() = runTest {
        val vm = HoldingsViewModel(
            getHoldingsUseCase = GetHoldingsUseCase(FakeSuccessRepository()),
            calculateSummaryUseCase = CalculateSummaryUseCase()
        )

        advanceUntilIdle()
        vm.toggleSummary()

        val success = vm.uiState.value as HoldingsUiState.Success
        assertTrue(success.isExpanded)
    }
}
