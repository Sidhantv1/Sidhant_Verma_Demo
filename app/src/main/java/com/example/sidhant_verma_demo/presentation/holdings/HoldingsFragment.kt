package com.example.sidhant_verma_demo.presentation.holdings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sidhant_verma_demo.R
import com.example.sidhant_verma_demo.data.HoldingsRepositoryImpl
import com.example.sidhant_verma_demo.data.local.db.AppDatabase
import com.example.sidhant_verma_demo.data.remote.RetrofitClient
import com.example.sidhant_verma_demo.databinding.FragmentHoldingsBinding
import com.example.sidhant_verma_demo.domain.usecase.CalculateSummaryUseCase
import com.example.sidhant_verma_demo.domain.usecase.GetHoldingsUseCase
import com.example.sidhant_verma_demo.presentation.utils.toRupee
import com.google.android.material.snackbar.Snackbar

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
        binding.summaryInclude.summaryHeader.setOnClickListener {
            viewModel.toggleSummary()
        }
        observeUi()
    }

    private fun setupViewModel() {
        val api = RetrofitClient.api
        val dao = AppDatabase.getInstance(requireContext()).holdingsDao()

        val repository = HoldingsRepositoryImpl(api, dao)
        val getUseCase = GetHoldingsUseCase(repository)
        val calculateSummary = CalculateSummaryUseCase()

        val factory = HoldingsViewModelFactory(getUseCase, calculateSummary)

        viewModel = ViewModelProvider(this, factory)[HoldingsViewModel::class.java]
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
                    is HoldingsUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerViewHoldings.visibility = View.GONE
                        binding.summaryInclude.root.visibility = View.GONE
                    }

                    is HoldingsUiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerViewHoldings.visibility = View.GONE
                        binding.summaryInclude.root.visibility = View.GONE
                        Snackbar.make(
                            requireView(),
                            "Failed to load holdings. Please try again.",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Retry") {
                                viewModel.loadHoldings()
                            }
                            .show()
                    }

                    is HoldingsUiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerViewHoldings.visibility = View.VISIBLE
                        binding.summaryInclude.root.visibility = View.VISIBLE

                        adapter.submitData(state.holdings)
                        val summary = state.summary
                        binding.summaryInclude.apply {
                            if (state.isExpanded) {
                                binding.summaryInclude.expandableSection.visibility = View.VISIBLE
                                binding.summaryInclude.ivExpand.rotation = 0f
                            } else {
                                binding.summaryInclude.expandableSection.visibility = View.GONE
                                binding.summaryInclude.ivExpand.rotation = 180f
                            }

                            "%.2f".format(summary.currentValue).toDouble().toRupee()
                                .also { tvCurrentValue.text = it }

                            "%.2f".format(summary.totalInvestment).toDouble().toRupee()
                                .also { tvTotalInvestment.text = it }

                            "%.2f".format(summary.todayPnL).toDouble().toRupee()
                                .also { tvTodayPnL.text = it }

                            "%.2f".format(summary.totalPnL).toDouble().toRupee()
                                .also { tvProfitLossValue.text = it }

                            tvTodayPnL.setTextColor(
                                if (summary.todayPnL >= 0)
                                    resources.getColor(R.color.green_shade, null)
                                else
                                    resources.getColor(R.color.red_shade, null)
                            )

                            tvProfitLossValue.setTextColor(
                                if (summary.totalPnL >= 0)
                                    resources.getColor(R.color.green_shade, null)
                                else
                                    resources.getColor(R.color.red_shade, null)
                            )
                        }
                    }
                }
            }
        }
    }
}
