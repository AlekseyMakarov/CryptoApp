package com.example.cryptoapp.domain

class LoadDataUseCase(private val repository: CoinInfoEntityListRepository) {
    operator fun invoke() {
        return repository.loadData()
    }
}