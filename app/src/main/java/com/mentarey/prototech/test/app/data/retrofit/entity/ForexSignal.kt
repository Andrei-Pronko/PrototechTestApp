package com.mentarey.prototech.test.app.data.retrofit.entity

import com.google.gson.annotations.SerializedName

data class ForexSignal(
    @SerializedName("ActualTime")
    val actualTime: Long,
    @SerializedName("Cmd")
    val cmd: Long,
    @SerializedName("Comment")
    val comment: String,
    @SerializedName("Id")
    val id: Long,
    @SerializedName("Pair")
    val pair: String,
    @SerializedName("Period")
    val period: String,
    @SerializedName("Price")
    val price: Double,
    @SerializedName("Sl")
    val sl: Double,
    @SerializedName("Tp")
    val tp: Double,
    @SerializedName("TradingSystem")
    val tradingSystem: Long
)