package com.example.cryptoapp.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class CoinWorkerFactory @Inject constructor(
    private val workerProviders: @JvmSuppressWildcards Map<Class<out WorkerChildFactory>, Provider<WorkerChildFactory>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            RefreshDataWorker::class.qualifiedName -> {
                workerProviders[RefreshDataWorker.Factory::class.java]
                    ?.get()
                    ?.create(appContext, workerParameters)
            }

            else -> null
        }
    }
}