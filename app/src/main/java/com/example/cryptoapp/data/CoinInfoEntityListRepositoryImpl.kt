package com.example.cryptoapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.workers.RefreshDataWorker
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.domain.CoinInfoEntityListRepository
import kotlinx.coroutines.delay

class CoinInfoEntityListRepositoryImpl(private val application: Application) :
    CoinInfoEntityListRepository {
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

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}