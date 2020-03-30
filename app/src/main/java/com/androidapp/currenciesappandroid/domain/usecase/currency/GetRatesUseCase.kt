package com.androidapp.currenciesappandroid.domain.usecase.currency

import com.androidapp.currenciesappandroid.domain.model.CurrencyRate
import com.androidapp.currenciesappandroid.domain.repository.Repository
import com.androidapp.currenciesappandroid.domain.usecase.BaseUseCase
import io.reactivex.Single

/**
 * Created by S.Nur Uysal on 2020-03-22.
 */
class GetRatesUseCase(private val repository: Repository) : BaseUseCase<GetRatesResult> {

    fun getCurrency(baseCurrency: String, baseValue: Double): Single<GetRatesResult> {
        return repository.getCurrencyRates(baseCurrency).map { result ->
            val newCurrencyRates: MutableList<CurrencyRate> = mutableListOf()
            if (result.error == null) {
                newCurrencyRates.add(CurrencyRate(baseCurrency, 1f, baseValue))

                result.value?.rates?.forEach { (currency, rate) ->
                    newCurrencyRates.add(CurrencyRate(currency, rate, rate * baseValue))
                }
                GetRatesResult.Success(newCurrencyRates)

            } else {
                GetRatesResult.Error(result.error)
            }
        }
    }

}