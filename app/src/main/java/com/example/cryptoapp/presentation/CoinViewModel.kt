package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp.data.repository.CoinInfoEntityListRepositoryImpl
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.domain.GetCoinInfoEntityListUseCase
import com.example.cryptoapp.domain.GetCoinInfoEntityUseCase
import com.example.cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CoinInfoEntityListRepositoryImpl(application)
    private val getCoinInfoEntityListUseCase = GetCoinInfoEntityListUseCase(repository)
    private val getCoinInfoEntityUseCase = GetCoinInfoEntityUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.IO)

    fun getDetailInfo(fSym: String): LiveData<CoinInfoEntity> {
        return getCoinInfoEntityUseCase(fSym)
    }

    fun getCoinInfoList(): LiveData<List<CoinInfoEntity>> {
        return getCoinInfoEntityListUseCase()
    }

    init {

        loadDataUseCase()
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}