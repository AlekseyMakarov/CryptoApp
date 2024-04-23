package com.example.domain

import androidx.lifecycle.LiveData

interface CoinPriceListRepository {
    fun getCoinPriceInfoList(): LiveData<List<CoinPriceInfoEntity>>
    fun getCoinPriceInfo(coinName: String): CoinPriceInfoEntity
}