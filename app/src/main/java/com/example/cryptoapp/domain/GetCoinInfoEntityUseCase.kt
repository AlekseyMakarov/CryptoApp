package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetCoinInfoEntityUseCase @Inject constructor(private val repository: CoinInfoEntityListRepository) {
    operator fun invoke(coinName: String): LiveData<CoinInfoEntity> {
        return repository.getCoinInfoEntity(coinName)
    }
}