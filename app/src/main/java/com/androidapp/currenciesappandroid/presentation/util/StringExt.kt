package com.androidapp.currenciesappandroid.presentation.util

import android.content.Context
import com.androidapp.currenciesappandroid.R
import java.text.NumberFormat
import java.util.*

/**
 * Created by S.Nur Uysal on 2020-03-26.
 */

fun String.toCurrencyValue(): Double {
    val nf: NumberFormat = NumberFormat.getInstance(Locale.getDefault())
    return nf.parse(this)!!.toDouble()
}

fun String.getCurrencyDescription(context: Context): String {
    val currencyDescName: String

    val resources = context.resources
    val packageName = context.packageName

    currencyDescName = try {
        resources.getString(resources.getIdentifier(this, "string", packageName))
    } catch (e: Exception) {
        "N/A"
    }
    return currencyDescName
}


fun String.getCurrencyFlag(context: Context): Int {
    var currencyFlag: Int

    val resources = context.resources
    val packageName = context.packageName
    val drawableName = "ic_" + this.substring(0, 2).toLowerCase(Locale.ROOT)

    currencyFlag = try {
        resources.getIdentifier(drawableName, "drawable", packageName)
    } catch (e: Exception) {
        0
    }

    if (currencyFlag == 0)
        currencyFlag = R.drawable.ic_eu

    return currencyFlag
}


