package com.androidapp.currenciesappandroid.data.repository

import com.androidapp.currenciesappandroid.data.service.ApiService
import com.androidapp.currenciesappandroid.domain.entity.CurrencyResponse
import com.androidapp.currenciesappandroid.domain.repository.Repository
import com.androidapp.currenciesappandroid.domain.repository.Result
import io.reactivex.Single

/**
 * Created by S.Nur Uysal on 2020-03-21.
 */

class RepositoryImpl : Repository {
    private val restApi = ApiService.create()
    override fun getCurrencyRates(baseRate: String): Single<Result<CurrencyResponse, Exception>> {
        return restApi.getCurrencyRates(baseRate)
            .map { response ->
                if (response.baseCurrency.isNotEmpty()) {
                    Result<CurrencyResponse, Exception>(response, null)

                } else {
                    Result(null, Exception())
                }
            }
            .onErrorReturn {
                Result<CurrencyResponse, Exception>(null, Exception())
            }
    }


}