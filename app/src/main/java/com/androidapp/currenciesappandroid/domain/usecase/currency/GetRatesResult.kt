package com.androidapp.currenciesappandroid.domain.usecase.currency

import com.androidapp.currenciesappandroid.domain.model.CurrencyRate
import com.androidapp.currenciesappandroid.domain.usecase.BaseUseCaseResult

/**
 * Created by S.Nur Uysal on 2020-03-22.
 */

sealed class GetRatesResult : BaseUseCaseResult {

    data class Success(val list: List<CurrencyRate>) : GetRatesResult()
    data class Error(val exception: Exception) : GetRatesResult()
}