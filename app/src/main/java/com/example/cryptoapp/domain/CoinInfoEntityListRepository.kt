package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData

interface CoinInfoEntityListRepository {
    fun getCoinInfoEntityList(): LiveData<List<CoinInfoEntity>>
    fun getCoinInfoEntity(coinName: String): LiveData<CoinInfoEntity>

    fun loadData()
}