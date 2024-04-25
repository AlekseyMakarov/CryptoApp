package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData

class GetCoinInfoEntityListUseCase(private val repository: CoinInfoEntityListRepository) {
    operator fun invoke(): LiveData<List<CoinInfoEntity>> {
        return repository.getCoinInfoEntityList()
    }
}