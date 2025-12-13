package com.example.sidhant_verma_demo.presentation.holdings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sidhant_verma_demo.databinding.ItemViewHoldingsBinding

class HoldingsAdapter : RecyclerView.Adapter<HoldingsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemViewHoldingsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemViewHoldingsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}