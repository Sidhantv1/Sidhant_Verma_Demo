package com.example.sidhant_verma_demo.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sidhant_verma_demo.presentation.holdings.HoldingsFragment

class PortfolioViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabs = PortfolioTab.entries.toTypedArray()

    override fun getItemCount() = tabs.size

    override fun createFragment(position: Int): Fragment {
        return when (tabs[position]) {
            PortfolioTab.POSITIONS -> PositionsFragment()
            PortfolioTab.HOLDINGS -> HoldingsFragment()
        }
    }
}
