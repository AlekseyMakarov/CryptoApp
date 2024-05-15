package com.example.cryptoapp.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.mappers.CoinMapper
import com.example.cryptoapp.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class RefreshDataWorker @Inject constructor(
    context: Context,
    workerParameters: WorkerParameters,
    private val apiService: ApiService,
    private val coinInfoDao: CoinInfoDao

) : CoroutineWorker(context, workerParameters) {

    //    private val apiService = ApiFactory.apiService
//    private val coinInfoDao = AppDatabase.getInstance(context).coinPriceInfoDao()
    override suspend fun doWork(): Result {

        while (true) {
            try {
                val coinNameListDto = apiService.getTopCoinsInfo(limit = 50)
                val coinNameString = CoinMapper.coinNameListDtoToString(coinNameListDto)
                val coinInfoJsonDto = apiService.getFullPriceList(fSyms = coinNameString)
                val listCoinInfoDto = CoinMapper.coinInfoJsonDtoToListCoinInfoDto(coinInfoJsonDto)
                coinInfoDao.insertPriceList(
                    CoinMapper.listCoinInfoDtoToListCoinInfoDBModel(listCoinInfoDto)
                )
            } catch (_: Exception) {

            }

            delay(10000)
        }
    }

    companion object {
        const val NAME = "RefreshDataWorker"
        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>()
                .build()
        }
    }

    class Factory @Inject constructor(
        private val apiService: ApiService,
        private val coinInfoDao: CoinInfoDao
    ) : WorkerChildFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return RefreshDataWorker(
                context,
                workerParameters,
                apiService,
                coinInfoDao
            )
        }
    }
}