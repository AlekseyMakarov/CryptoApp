package com.example.cryptoapp

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.workers.RefreshDataWorkerFactory
import com.example.cryptoapp.di.DaggerApplicationComponent

class CryptoApplication : Application(), Configuration.Provider {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(
                RefreshDataWorkerFactory(
                    ApiFactory.apiService,
                    AppDatabase.getInstance(this).coinPriceInfoDao()
                )
            ).build()
}