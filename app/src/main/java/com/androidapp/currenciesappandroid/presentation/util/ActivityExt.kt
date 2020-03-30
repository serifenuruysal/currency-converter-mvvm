package com.androidapp.currenciesappandroid.presentation.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Created by S.Nur Uysal on 2020-03-24.
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment, addToStack: Boolean) {
    supportFragmentManager.executePendingTransactions()
    supportFragmentManager.inTransaction {
        if (addToStack) add(frameId, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
        else add(frameId, fragment)
    }
}
