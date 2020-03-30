package com.androidapp.currenciesappandroid.domain.repository

import com.androidapp.currenciesappandroid.domain.entity.CurrencyResponse
import io.reactivex.Single

/**
 * Created by S.Nur Uysal on 2020-03-21.
 */

interface Repository {

    fun getCurrencyRates(baseRate: String): Single<Result<CurrencyResponse, Exception>>
}