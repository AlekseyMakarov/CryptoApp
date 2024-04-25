package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.ActivityCoinPrceListBinding
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinPrceListBinding
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPrceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfoEntity) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinInfo.fromSymbol
                )
                startActivity(intent)
            }
        }
        val rvCoinPriceList = binding.rvCoinPriceList
        rvCoinPriceList.adapter = adapter
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getCoinInfoList().observe(this) {
            adapter.submitList(it)
        }
    }
}
