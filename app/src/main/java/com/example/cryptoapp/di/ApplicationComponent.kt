package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.CryptoApplication
import com.example.cryptoapp.presentation.CoinDetailActivity
import com.example.cryptoapp.presentation.CoinDetailFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DomainModule::class,
        DataModule::class,
        ViewModelModule::class,
        WorkerModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: CoinDetailFragment)

    fun inject(application: CryptoApplication)

    fun inject(activity: CoinDetailActivity)

    fun activityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}