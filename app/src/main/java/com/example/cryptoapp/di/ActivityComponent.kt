package com.example.cryptoapp.di

import android.content.Context
import com.example.cryptoapp.presentation.CoinListFragment
import com.example.cryptoapp.presentation.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent()
interface ActivityComponent {

    fun inject(activity: CoinPriceListActivity)
    fun inject(fragment: CoinListFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance @CoinListActivityQualifier  context: Context,
        ): ActivityComponent
    }
}