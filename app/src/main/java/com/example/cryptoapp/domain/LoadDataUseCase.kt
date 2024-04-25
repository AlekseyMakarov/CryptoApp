package com.example.cryptoapp.domain

class LoadDataUseCase(private val repository: CoinInfoEntityListRepository) {
    suspend operator fun invoke() {
        return repository.loadData()
    }
}