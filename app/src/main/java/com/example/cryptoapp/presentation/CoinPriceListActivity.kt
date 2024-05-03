package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPrceListBinding

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinPrceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPrceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CoinListFragment())
                .commit()
        }

        val fragment = supportFragmentManager.fragments.find { it.tag == "details" }
        if (fragment != null) {
            if (Utils.isLandscape(this)) {
                if (fragment.id != R.id.fragment_container_land) {

                    supportFragmentManager.popBackStackImmediate(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager.beginTransaction()
                        .remove(fragment)
                        .commit()
                    supportFragmentManager.executePendingTransactions()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_land, fragment, "details")
                        .addToBackStack(null)
                        .commit()
                }
            } else {
                if (fragment.id != R.id.fragment_container) {
                    supportFragmentManager.popBackStackImmediate(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager.beginTransaction()
                        .remove(fragment)
                        .commit()
                    supportFragmentManager.executePendingTransactions()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, "details")
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }
}
