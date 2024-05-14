package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetCoinInfoEntityListUseCase @Inject constructor(private val repository: CoinInfoEntityListRepository) {
    operator fun invoke(): LiveData<List<CoinInfoEntity>> {
        return repository.getCoinInfoEntityList()
    }
}