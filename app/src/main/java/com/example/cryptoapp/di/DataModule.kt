package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiService
import com.example.cryptoapp.data.repository.CoinInfoEntityListRepositoryImpl
import com.example.cryptoapp.domain.CoinInfoEntityListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepository(impl: CoinInfoEntityListRepositoryImpl): CoinInfoEntityListRepository

//    @Binds
//    fun bindContext(application: Application): Context

    companion object {
        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(application: Application): CoinInfoDao {
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provide():ApiService{
            return ApiFactory.apiService
        }
    }
}