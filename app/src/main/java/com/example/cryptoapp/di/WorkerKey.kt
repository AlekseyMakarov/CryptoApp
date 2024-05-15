package com.example.cryptoapp.di

import androidx.work.ListenableWorker
import com.example.cryptoapp.data.workers.WorkerChildFactory
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out WorkerChildFactory>) {
}