package com.example.cryptoapp.di

import com.example.cryptoapp.data.workers.RefreshDataWorker
import com.example.cryptoapp.data.workers.WorkerChildFactory
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