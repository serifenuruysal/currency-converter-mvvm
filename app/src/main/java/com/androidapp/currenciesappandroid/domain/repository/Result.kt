package com.androidapp.currenciesappandroid.domain.repository

/**
 * Created by S.Nur Uysal on 2020-03-22.
 */
class Result<out V : Any, out E : Exception>(
    val value: V?,
    val error: E?
)
