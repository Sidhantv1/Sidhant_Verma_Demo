package com.example.sidhant_verma_demo.presentation.holdings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sidhant_verma_demo.databinding.FragmentHoldingsBinding

class HoldingsFragment : Fragment() {

    private val fragmentHoldingsBinding by lazy {
        FragmentHoldingsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return fragmentHoldingsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        fragmentHoldingsBinding.recyclerViewHoldings.adapter = HoldingsAdapter()
        fragmentHoldingsBinding.recyclerViewHoldings.setHasFixedSize(true)
        fragmentHoldingsBinding.recyclerViewHoldings.layoutManager =
            LinearLayoutManager(requireActivity())
    }

}