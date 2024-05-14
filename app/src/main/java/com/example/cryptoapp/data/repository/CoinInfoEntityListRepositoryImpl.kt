package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.mappers.CoinMapper
import com.example.cryptoapp.data.workers.RefreshDataWorker
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.domain.CoinInfoEntityListRepository
import javax.inject.Inject

class CoinInfoEntityListRepositoryImpl @Inject constructor(
    private val application: Application,
    private val coinInfoDao: CoinInfoDao
) :
    CoinInfoEntityListRepository {
    override fun getCoinInfoEntityList(): LiveData<List<CoinInfoEntity>> {
        return MediatorLiveData<List<CoinInfoEntity>>().apply {
            addSource(coinInfoDao.getPriceList()) {
                value = CoinMapper.listCoinInfoDBModelToListCoinInfoEntity(it)
            }
        }
    }

    override fun getCoinInfoEntity(coinName: String): LiveData<CoinInfoEntity> {
        return MediatorLiveData<CoinInfoEntity>().apply {
            addSource(coinInfoDao.getPriceInfoAboutCoin(coinName)) {
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