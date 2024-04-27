package com.example.cryptoapp.data

import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.CoinInfoDto
import com.example.cryptoapp.data.network.CoinInfoJsonDto
import com.example.cryptoapp.data.network.CoinNameListDto
import com.example.cryptoapp.data.database.CoinInfoDBModel
import com.example.cryptoapp.domain.CoinInfoEntity
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CoinMapper {

    companion object {
        fun coinNameListDtoToString(coinNameListDto: CoinNameListDto): String {
            return coinNameListDto.coinNameContainerList?.map { it.coinName?.name }
                ?.joinToString(separator = ",") { it ?: "" }
                ?: ""
        }

        fun coinInfoDtoToCoinInfoDBModel(coinInfoDto: CoinInfoDto): CoinInfoDBModel {
            with(coinInfoDto) {
                return CoinInfoDBModel(
                    fromSymbol,
                    toSymbol,
                    price,
                    lastUpdate,
                    highDay,
                    lowDay,
                    lastMarket,
                    getFullImageUrl(imageUrl)
                )
            }
        }

        fun listCoinInfoDBModelToListCoinInfoEntity(list: List<CoinInfoDBModel>):
                List<CoinInfoEntity> {
            return list.map { coinInfoDBModelToCoinInfoEntity(it) }
        }

        fun coinInfoDBModelToCoinInfoEntity(coin: CoinInfoDBModel): CoinInfoEntity {
            with(coin) {
                return CoinInfoEntity(
                    fromSymbol,
                    toSymbol,
                    price,
                    mapTimestampToFormattedTime(lastUpdate),
                    highDay,
                    lowDay,
                    lastMarket,
                    imageUrl
                )
            }
        }

        fun listCoinInfoDtoToListCoinInfoDBModel(listCoinInfoDto: List<CoinInfoDto>):
                List<CoinInfoDBModel> {
            return listCoinInfoDto.map { coinInfoDtoToCoinInfoDBModel(it) }
        }

        fun coinInfoJsonDtoToListCoinInfoDto(
            coinPriceInfoRawData: CoinInfoJsonDto
        ): List<CoinInfoDto> {
            val result = ArrayList<CoinInfoDto>()
            val jsonObject = coinPriceInfoRawData.json ?: return result
            val coinKeySet = jsonObject.keySet()
            for (coinKey in coinKeySet) {
                val currencyJson = jsonObject.getAsJsonObject(coinKey)
                val currencyKeySet = currencyJson.keySet()
                for (currencyKey in currencyKeySet) {
                    val priceInfo = Gson().fromJson(
                        currencyJson.getAsJsonObject(currencyKey),
                        CoinInfoDto::class.java
                    )
                    result.add(priceInfo)
                }
            }
            return result
        }

        fun mapTimestampToFormattedTime(timestamp: Long?): String {
            if (timestamp == null) return ""
            val stamp = Timestamp(timestamp * 1000)
            val date = Date(stamp.time)
            val pattern = "HH:mm:ss"
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(date)
        }

        fun getFullImageUrl(imageUrl: String?): String {
            return ApiFactory.BASE_IMAGE_URL + imageUrl
        }
    }
}