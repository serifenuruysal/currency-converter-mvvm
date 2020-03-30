package com.androidapp.currenciesappandroid.presentation.viewmodel

import com.androidapp.currenciesappandroid.domain.model.CurrencyRate

/**
 * Created by S.Nur Uysal on 2020-03-24.
 */

sealed class RatesState {
    abstract val loadedAllItems: Boolean
    abstract val ratesList: List<CurrencyRate>?
    abstract val baseCurrency: String?
    abstract val baseValue: Double?
}

data class DefaultState(
    override val loadedAllItems: Boolean,
    override val ratesList: List<CurrencyRate>?,
    override val baseCurrency: String?,
    override val baseValue: Double?
) : RatesState()

data class LoadedState(
    override val loadedAllItems: Boolean,
    override val ratesList: List<CurrencyRate>?,
    override val baseCurrency: String?,
    override val baseValue: Double?
) : RatesState()

data class RefreshValueListState(
    override val loadedAllItems: Boolean,
    override val ratesList: List<CurrencyRate>?,
    override val baseCurrency: String?,
    override val baseValue: Double?
) : RatesState()

data class ErrorState(
    val errorMessage: String,
    override val loadedAllItems: Boolean,
    override val ratesList: List<CurrencyRate>?,
    override val baseCurrency: String?,
    override val baseValue: Double?
) : RatesState()