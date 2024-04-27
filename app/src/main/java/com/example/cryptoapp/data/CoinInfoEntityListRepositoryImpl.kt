package com.example.cryptoapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.domain.CoinInfoEntityListRepository
import kotlinx.coroutines.delay

class CoinInfoEntityListRepositoryImpl(application: Application) : CoinInfoEntityListRepository {
    private val apiService = ApiFactory.apiService
    private val database = AppDatabase.getInstance(application).coinPriceInfoDao()
    override fun getCoinInfoEntityList(): LiveData<List<CoinInfoEntity>> {
        return MediatorLiveData<List<CoinInfoEntity>>().apply {
            addSource(database.getPriceList()) {
                value = CoinMapper.listCoinInfoDBModelToListCoinInfoEntity(it)
            }
        }
    }

    override fun getCoinInfoEntity(coinName: String): LiveData<CoinInfoEntity> {
        return MediatorLiveData<CoinInfoEntity>().apply {
            addSource(database.getPriceInfoAboutCoin(coinName)) {
                value = CoinMapper.coinInfoDBModelToCoinInfoEntity(it)
            }
        }
    }

    override suspend fun loadData() {
        while (true) {
            try {
                val coinNameListDto = apiService.getTopCoinsInfo(limit = 50)
                val coinNameString = CoinMapper.coinNameListDtoToString(coinNameListDto)
                val coinInfoJsonDto = apiService.getFullPriceList(fSyms = coinNameString)
                val listCoinInfoDto = CoinMapper.coinInfoJsonDtoToListCoinInfoDto(coinInfoJsonDto)
                database.insertPriceList(
                    CoinMapper.listCoinInfoDtoToListCoinInfoDBModel(listCoinInfoDto)
                )
            } catch (_: Exception) {

            }

            delay(10000)
        }
    }
}