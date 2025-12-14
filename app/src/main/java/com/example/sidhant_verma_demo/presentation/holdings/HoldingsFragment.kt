package com.example.sidhant_verma_demo.presentation.holdings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sidhant_verma_demo.R
import com.example.sidhant_verma_demo.data.HoldingsRepositoryImpl
import com.example.sidhant_verma_demo.data.local.db.AppDatabase
import com.example.sidhant_verma_demo.data.remote.RetrofitClient
import com.example.sidhant_verma_demo.databinding.FragmentHoldingsBinding
import com.example.sidhant_verma_demo.domain.usecase.CalculateSummaryUseCase
import com.example.sidhant_verma_demo.domain.usecase.GetHoldingsUseCase
import com.example.sidhant_verma_demo.presentation.utils.toPercentage
import com.example.sidhant_verma_demo.presentation.utils.toRupee
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                            showErrorSnackbar()
                        }

                        is HoldingsUiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerViewHoldings.visibility = View.VISIBLE
                            binding.summaryInclude.root.visibility = View.VISIBLE

                            adapter.submitData(state.holdings)
                            val summary = state.summary
                            val pnlValue = summary.totalPnL
                            val pnlPercentage = if (summary.totalInvestment != 0.0) {
                                (pnlValue / summary.totalInvestment) * 100
                            } else 0.0
                            val pnlRupee = "%.2f".format(summary.totalPnL).toDouble().toRupee()
                            val pnlPercent = pnlPercentage.toPercentage()
                            binding.summaryInclude.apply {
                                if (state.isExpanded) {
                                    binding.summaryInclude.expandableSection.visibility =
                                        View.VISIBLE
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

                                "$pnlRupee ($pnlPercent)".also { tvProfitLossValue.text = it }

                                tvTodayPnL.setTextColor(
                                    if (summary.todayPnL >= 0)
                                        ContextCompat.getColor(
                                            requireContext(),
                                            R.color.green_shade
                                        )
                                    else
                                        ContextCompat.getColor(requireContext(), R.color.red_shade)
                                )

                                tvProfitLossValue.setTextColor(
                                    if (summary.totalPnL >= 0)
                                        ContextCompat.getColor(
                                            requireContext(),
                                            R.color.green_shade
                                        )
                                    else
                                        ContextCompat.getColor(requireContext(), R.color.red_shade)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showErrorSnackbar() {
        val parent = requireActivity().findViewById<View>(android.R.id.content)
        val snackBar = Snackbar.make(
            parent,
            getString(R.string.failed_to_load_holdings_please_try_again),
            Snackbar.LENGTH_LONG
        )

        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.red_shade)
        )

        snackBar.setTextColor(
            ContextCompat.getColor(requireContext(), R.color.white_shade)
        )

        snackBar.setActionTextColor(
            ContextCompat.getColor(requireContext(), R.color.white_shade)
        )

        snackBar.setAction("Retry") {
            viewModel.loadHoldings()
        }
        snackBar.show()
    }
}
