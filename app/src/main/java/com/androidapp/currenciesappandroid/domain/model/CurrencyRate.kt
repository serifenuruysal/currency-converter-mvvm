package com.androidapp.currenciesappandroid.domain.model

/**
 * Created by S.Nur Uysal on 2020-03-23.
 */
data class CurrencyRate(
    val currency: String,
    val rate: Float,
    var value: Double
)