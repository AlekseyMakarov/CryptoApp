package com.example.cryptoapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.domain.GetCoinInfoEntityListUseCase
import com.example.cryptoapp.domain.GetCoinInfoEntityUseCase
import com.example.cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoEntityListUseCase: GetCoinInfoEntityListUseCase,
    private val getCoinInfoEntityUseCase: GetCoinInfoEntityUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {


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