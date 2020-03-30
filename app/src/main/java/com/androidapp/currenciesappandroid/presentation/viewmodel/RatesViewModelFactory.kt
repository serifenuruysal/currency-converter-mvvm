package com.androidapp.currenciesappandroid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by S.Nur Uysal on 2020-03-24.
 */
@Suppress("UNCHECKED_CAST")
class RatesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RatesViewModel() as T
    }
}