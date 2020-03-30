package com.androidapp.currenciesappandroid.data.service

import com.androidapp.currenciesappandroid.domain.entity.CurrencyResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by S.Nur Uysal on 2020-03-21.
 */

interface Api {
    @GET("android/latest")
    fun getCurrencyRates(@Query("base") baseRate: String): Single<CurrencyResponse>
}
