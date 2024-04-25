package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData

class GetCoinInfoEntityUseCase(private val repository: CoinInfoEntityListRepository) {
    operator fun invoke(coinName: String): LiveData<CoinInfoEntity> {
        return repository.getCoinInfoEntity(coinName)
    }
}