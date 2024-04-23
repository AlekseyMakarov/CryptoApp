package com.example.domain

class GetCoinPriceInfoUseCase(val repository: CoinPriceListRepository) {
    operator fun invoke(coinName: String): CoinPriceInfoEntity{
        return repository.getCoinPriceInfo(coinName)
    }
}