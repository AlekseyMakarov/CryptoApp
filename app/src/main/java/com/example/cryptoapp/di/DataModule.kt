package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.repository.CoinInfoEntityListRepositoryImpl
import com.example.cryptoapp.domain.CoinInfoEntityListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: CoinInfoEntityListRepositoryImpl): CoinInfoEntityListRepository

//    @Binds
//    fun bindContext(application: Application): Context

    companion object {
        @Provides
        fun provideCoinInfoDao(application: Application): CoinInfoDao {
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }
    }
}