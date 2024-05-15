package com.example.cryptoapp.di

import androidx.lifecycle.ViewModel
import androidx.work.ListenableWorker
import com.example.cryptoapp.data.workers.RefreshDataWorker
import com.example.cryptoapp.data.workers.WorkerChildFactory
import com.example.cryptoapp.presentation.CoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {
    @IntoMap
    @WorkerKey(RefreshDataWorker.Factory::class)
    @Binds
    fun bindRefreshDataWorker(impl: RefreshDataWorker.Factory): WorkerChildFactory
}