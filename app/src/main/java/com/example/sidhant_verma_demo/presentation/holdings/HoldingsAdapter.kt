package com.example.sidhant_verma_demo.presentation.holdings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sidhant_verma_demo.databinding.ItemViewHoldingsBinding

class HoldingsAdapter :
    RecyclerView.Adapter<HoldingsAdapter.ViewHolder>() {

    private val items = mutableListOf<HoldingsUiModel>()

    inner class ViewHolder(val binding: ItemViewHoldingsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewHoldingsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {

            tvStockName.text = item.symbol
            tvLtp.text = "LTP: ${item.ltpFormatted}"
            tvNetQty.text = "NET QTY: ${item.quantity}"
            tvPl.text = "P&L: ${item.pnlFormatted}"
            tvPl.setTextColor(item.pnlColor)
        }
    }

    fun submitData(newList: List<HoldingsUiModel>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}
