package com.androidapp.currenciesappandroid.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.currenciesappandroid.R
import com.androidapp.currenciesappandroid.domain.model.CurrencyRate
import com.androidapp.currenciesappandroid.presentation.adapter.CurrencySelectEvent
import com.androidapp.currenciesappandroid.presentation.adapter.CurrencyValueChangedEvent
import com.androidapp.currenciesappandroid.presentation.adapter.RatesAdapter
import com.androidapp.currenciesappandroid.presentation.rx.RxBus
import com.androidapp.currenciesappandroid.presentation.viewmodel.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_rates.*

/**
 * Created by S.Nur Uysal on 2020-03-24.
 */

class RatesFragment : Fragment() {

    private lateinit var subscribeCurrencySelectEvent: Disposable
    private lateinit var subscribeCurrencyValueChangedEvent: Disposable
    private lateinit var rateListCurrencyRatesAdapter: RatesAdapter

    private var isLoading = false

    private val viewModel: RatesViewModel
            by lazy {
                ViewModelProviders.of(this, context?.let { RatesViewModelFactory() }).get(
                    RatesViewModel::class.java
                )
            }
    private val stateObserver = Observer<RatesState> { state ->
        state?.let {
            when (state) {
                is LoadedState -> {
                    isLoading = false
                    it.ratesList?.let { list ->
                        initRatesList(list)
                    }

                }
                is DefaultState -> {
                    isLoading = true
                }
                is RefreshValueListState -> {
                    isLoading = false
                    it.ratesList?.let { list ->
                        rateListCurrencyRatesAdapter.dispatchListUpdate(list)
                    }

                }
                is ErrorState -> {
                    isLoading = false
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        subscribeObservable()
        viewModel.getCurrency()

    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(this, stateObserver)
    }

    private fun subscribeObservable() {

        subscribeCurrencySelectEvent =
            RxBus.listen(CurrencySelectEvent::class.java).subscribe {
                viewModel.setNewCurrency(it.currencyName, it.value)

            }
        subscribeCurrencyValueChangedEvent =
            RxBus.listen(CurrencyValueChangedEvent::class.java).subscribe {
                viewModel.setNewCurrencyValue(it.value)
            }
    }

    private fun unSubscribeObservable() {
        if (!subscribeCurrencySelectEvent.isDisposed) {
            subscribeCurrencySelectEvent.dispose()
        }
        if (!subscribeCurrencyValueChangedEvent.isDisposed) {
            subscribeCurrencyValueChangedEvent.dispose()
        }

        viewModel.stateLiveData.removeObserver(stateObserver)
    }

    private fun initRatesList(list: List<CurrencyRate>) {
        rv_list.let {
            rv_list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            rateListCurrencyRatesAdapter = RatesAdapter(list)
            rv_list.adapter = rateListCurrencyRatesAdapter

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unSubscribeObservable()
    }

}