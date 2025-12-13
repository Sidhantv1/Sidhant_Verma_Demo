package com.example.sidhant_verma_demo.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sidhant_verma_demo.R
import com.example.sidhant_verma_demo.databinding.FragmentPortfolioBinding
import com.google.android.material.tabs.TabLayoutMediator

class PortfolioFragment : Fragment() {
    private lateinit var portfolioViewPagerAdapter: PortfolioViewPagerAdapter

    private val fragmentPortfolioBinding by lazy {
        FragmentPortfolioBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return fragmentPortfolioBinding.root
    }

    val tabs = PortfolioTab.entries.toTypedArray()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        portfolioViewPagerAdapter = PortfolioViewPagerAdapter(this)
        fragmentPortfolioBinding.viewPager.adapter = portfolioViewPagerAdapter
        TabLayoutMediator(
            fragmentPortfolioBinding.tabLayout,
            fragmentPortfolioBinding.viewPager
        ) { tab, position ->
            tab.text = getString(tabs[position].titleRes)
        }.attach()
    }
}

enum class PortfolioTab(val titleRes: Int) {
    POSITIONS(R.string.positions),
    HOLDINGS(R.string.holdings)
}