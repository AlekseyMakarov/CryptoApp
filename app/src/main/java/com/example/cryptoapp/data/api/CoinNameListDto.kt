package com.example.cryptoapp.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNameListDto(
    @SerializedName("Data")
    @Expose
    val coinNameContainerList: List<CoinNameContainerDto>? = null
)
