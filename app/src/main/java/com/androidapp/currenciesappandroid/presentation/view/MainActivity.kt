package com.androidapp.currenciesappandroid.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidapp.currenciesappandroid.R
import com.androidapp.currenciesappandroid.presentation.util.addFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (fragmentsContainer != null) {
            if (savedInstanceState != null) {
                return
            }
            addFragment(R.id.fragmentsContainer, RatesFragment(), true)
        }
    }

}
