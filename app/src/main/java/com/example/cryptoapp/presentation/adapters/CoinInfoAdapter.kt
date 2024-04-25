package com.example.cryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.data.CoinMapper
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.domain.CoinInfoEntity
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinInfoEntity> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_coin_info,
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        when (holder.binding) {
            is ItemCoinInfoBinding -> {
                with(holder.binding) {
                    with(coin) {
                        val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                        val lastUpdateTemplate =
                            context.resources.getString(R.string.last_update_template)
                        tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                        tvPrice.text = price
                        tvLastUpdate.text = String.format(
                            lastUpdateTemplate,
                            CoinMapper.mapTimestampToString(lastUpdate)
                        )
                        Picasso.get().load(CoinMapper.getFullImageUrl(imageUrl)).into(ivLogoCoin)
                        root.setOnClickListener {
                            onCoinClickListener?.onCoinClick(this)
                        }
                    }
                }
            }
        }
    }

    inner class CoinInfoViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnCoinClickListener {
        fun onCoinClick(coinInfo: CoinInfoEntity)
    }
}