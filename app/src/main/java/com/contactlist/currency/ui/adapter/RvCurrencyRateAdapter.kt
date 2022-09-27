package com.contactlist.currency.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.contactlist.currency.databinding.ItemCurrenciesBinding

class RvCurrencyRateAdapter : RecyclerView.Adapter<RvCurrencyRateAdapter.CurrencyViewHolder>() {

    private var currencyData: HashMap<String, Double> = HashMap()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val itemBinding =
            ItemCurrenciesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(currencyViewHolder: CurrencyViewHolder, position: Int) {
        val itemKey = currencyData.keys.elementAt(position)
        currencyViewHolder.bind(currencyData.keys.elementAt(position), currencyData.get(itemKey))
    }

    override fun getItemCount() = currencyData.size

    inner class CurrencyViewHolder(private val itemBinding: ItemCurrenciesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(title: String, rate: Double?) = with(itemView) {
            itemBinding.tvTitle.text = title
            itemBinding.tvRate.text = rate.toString()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refereshList(items: HashMap<String, Double>) {
        currencyData = items
        notifyDataSetChanged()
    }
}