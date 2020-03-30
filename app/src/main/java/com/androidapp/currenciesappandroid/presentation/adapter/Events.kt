package com.androidapp.currenciesappandroid.presentation.adapter

/**
 * Created by S.Nur Uysal on 2020-03-25.
 */


data class CurrencySelectEvent(val currencyName: String, val value: Double)

data class CurrencyValueChangedEvent(val value: Double)