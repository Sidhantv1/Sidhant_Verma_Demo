package com.example.sidhant_verma_demo.presentation.holdings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sidhant_verma_demo.data.HoldingsRepositoryImpl
import com.example.sidhant_verma_demo.data.local.db.AppDatabase
import com.example.sidhant_verma_demo.data.remote.RetrofitClient
import com.example.sidhant_verma_demo.databinding.FragmentHoldingsBinding
import com.example.sidhant_verma_demo.domain.usecase.CalculateSummaryUseCase
import com.example.sidhant_verma_demo.domain.usecase.GetHoldingsUseCase

class HoldingsFragment : Fragment() {

    private lateinit var viewModel: HoldingsViewModel
    private lateinit var adapter: HoldingsAdapter

    private val binding by lazy {
        FragmentHoldingsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAdapter()
        observeUi()
        viewModel.loadHoldings()
    }

    private fun setupViewModel() {
        val api = RetrofitClient.api
        val dao = AppDatabase.getInstance(requireContext()).holdingsDao()

        val repository = HoldingsRepositoryImpl(api, dao)
        val getUseCase = GetHoldingsUseCase(repository)
        val calculateSummary = CalculateSummaryUseCase()

        viewModel = HoldingsViewModel(getUseCase, calculateSummary)
    }

    private fun setupAdapter() {
        adapter = HoldingsAdapter()
        binding.recyclerViewHoldings.adapter = adapter
        binding.recyclerViewHoldings.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun observeUi() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                when (state) {
                    is HoldingsUiState.Loading -> Unit
                    is HoldingsUiState.Error -> Unit
                    is HoldingsUiState.Success -> {
                        adapter.submitData(state.holdings)
                    }
                }
            }
        }
    }
}
