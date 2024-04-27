package com.example.cryptoapp.data

import com.example.cryptoapp.data.api.ApiFactory
import com.example.cryptoapp.data.api.CoinInfoDto
import com.example.cryptoapp.data.api.CoinInfoJsonDto
import com.example.cryptoapp.data.api.CoinNameListDto
import com.example.cryptoapp.data.database.CoinInfoDBModel
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.utils.convertTimestampToTime
import com.google.gson.Gson

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
            return convertTimestampToTime(timestamp)
        }

        fun getFullImageUrl(imageUrl: String?): String {
            return ApiFactory.BASE_IMAGE_URL + imageUrl
        }
    }
}