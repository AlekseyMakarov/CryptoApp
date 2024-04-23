package com.example.domain

import androidx.lifecycle.LiveData

class GetCoinPriceInfoListUseCase(private val repository: CoinPriceListRepository) {
    operator fun invoke(): LiveData<List<CoinPriceInfoEntity>> {
        return repository.getCoinPriceInfoList()
    }
}