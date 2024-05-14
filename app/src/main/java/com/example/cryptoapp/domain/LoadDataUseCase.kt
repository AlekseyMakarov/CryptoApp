package com.example.cryptoapp.domain

import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: CoinInfoEntityListRepository) {
    operator fun invoke() {
        return repository.loadData()
    }
}