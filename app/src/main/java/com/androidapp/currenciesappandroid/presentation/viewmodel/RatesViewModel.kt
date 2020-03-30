package com.androidapp.currenciesappandroid.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidapp.currenciesappandroid.data.repository.RepositoryImpl
import com.androidapp.currenciesappandroid.domain.model.CurrencyRate
import com.androidapp.currenciesappandroid.domain.usecase.BaseUseCaseResult
import com.androidapp.currenciesappandroid.domain.usecase.currency.GetRatesResult
import com.androidapp.currenciesappandroid.domain.usecase.currency.GetRatesUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by S.Nur Uysal on 2020-03-24.
 */

class RatesViewModel : ViewModel() {

    val stateLiveData = MutableLiveData<RatesState>()
    private var repository = RepositoryImpl()
    private lateinit var disposableGetCurrencyTimer: Disposable
    private var getRatesUseCase: GetRatesUseCase

    init {
        // Default values
        stateLiveData.value =
            DefaultState(false, emptyList(), "EUR", 100.0)

        getRatesUseCase = GetRatesUseCase(repository)
    }

    @SuppressLint("CheckResult")
    fun getCurrency() {

        disposableGetCurrencyTimer = Observable.interval(0, 1, TimeUnit.MINUTES)
            .doOnNext {
                getRatesUseCase.getCurrency(obtainBaseCurrency()!!, obtainBaseCurrencyValue()!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onDataResponseReceived, this::onError)
                getRatesUseCase.getCurrency(obtainBaseCurrency()!!, obtainBaseCurrencyValue()!!)
                    .toObservable()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }


    private fun onDataResponseReceived(result: BaseUseCaseResult) {
        when (result) {
            is GetRatesResult.Success -> {
                stateLiveData.value =
                    LoadedState(
                        true,
                        result.list,
                        obtainBaseCurrency(),
                        obtainBaseCurrencyValue()
                    )

            }
            is GetRatesResult.Error -> {
                stateLiveData.value =
                    ErrorState(
                        result.exception.message!!,
                        false,
                        emptyList(),
                        obtainBaseCurrency(),
                        obtainBaseCurrencyValue()
                    )
            }
        }
    }

    private fun onError(error: Throwable) {
        stateLiveData.value =
            ErrorState(
                error.message ?: "",
                false,
                emptyList(),
                obtainBaseCurrency(),
                obtainBaseCurrencyValue()
            )
    }

    fun setNewCurrency(baseCurrency: String, currencyValue: Double) {
        if (baseCurrency != obtainBaseCurrency()) {
            stateLiveData.value =
                DefaultState(true, obtainCurrencyRateList(), baseCurrency, currencyValue)
            if (disposableGetCurrencyTimer.isDisposed)
                disposableGetCurrencyTimer.dispose()
            getCurrency()
        }
    }

    fun setNewCurrencyValue(currencyValue: Double) {
        if (currencyValue != obtainBaseCurrencyValue()) {
            val newRatesList = mutableListOf<CurrencyRate>()
            obtainCurrencyRateList()?.forEach {
                newRatesList.add(CurrencyRate(it.currency, it.rate, it.rate * currencyValue))

            }

            stateLiveData.value =
                RefreshValueListState(true, newRatesList, obtainBaseCurrency(), currencyValue)
        }
    }

    private fun obtainBaseCurrency() = stateLiveData.value?.baseCurrency

    private fun obtainBaseCurrencyValue() = stateLiveData.value?.baseValue

    private fun obtainCurrencyRateList() = stateLiveData.value?.ratesList


}