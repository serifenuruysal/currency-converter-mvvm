package com.androidapp.currenciesappandroid.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    val baseCurrency: String,

    @SerializedName("rates")
    @Expose
    val rates: Map<String, Float>? = null
)