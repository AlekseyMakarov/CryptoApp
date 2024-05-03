package com.example.cryptoapp.presentation

import android.content.Context
import android.content.res.Configuration

class Utils {
    companion object{
        fun isLandscape(context: Context): Boolean {
            return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        }
    }
}