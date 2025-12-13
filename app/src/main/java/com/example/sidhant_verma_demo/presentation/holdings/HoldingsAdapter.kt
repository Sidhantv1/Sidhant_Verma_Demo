package com.example.sidhant_verma_demo.presentation.holdings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sidhant_verma_demo.databinding.ItemViewHoldingsBinding
import com.example.sidhant_verma_demo.presentation.utils.toRupee

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
            "LTP: ${((item.ltpFormatted).toDouble().toRupee())}".also { tvLtp.text = it }
            "NET QTY: ${item.quantity}".also { tvNetQty.text = it }
            "P&L: ${(item.pnlFormatted).toDouble().toRupee()}".also { tvPl.text = it }
            tvPl.setTextColor(
                ContextCompat.getColor(holder.itemView.context, item.pnlColorRes)
            )
        }
    }

    fun submitData(newList: List<HoldingsUiModel>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}
