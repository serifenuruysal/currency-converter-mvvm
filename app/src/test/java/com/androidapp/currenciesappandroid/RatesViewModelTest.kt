package com.androidapp.currenciesappandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.androidapp.currenciesappandroid.presentation.viewmodel.*
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations


/**
 * Created by S.Nur Uysal on 2020-03-28.
 */

@RunWith(AndroidJUnit4::class)
class RatesViewModelTest {

    private lateinit var viewModel: RatesViewModel


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = RatesViewModel()

    }

    @Test
    fun `check response data is not null after data emittied by vm `() {

        viewModel.getCurrency()
        viewModel.stateLiveData.observeForTesting {
            MatcherAssert.assertThat(
                viewModel.stateLiveData.value?.ratesList,
                (CoreMatchers.not(Matchers.nullValue()))
            )
        }

    }


    @Test
    fun `check all data loaded after data emittied by vm  `() {

        viewModel.getCurrency()
        viewModel.stateLiveData.observeForTesting {
            MatcherAssert.assertThat(
                viewModel.stateLiveData.value?.loadedAllItems,
                CoreMatchers.`is`(false)
            )
        }

    }

    @Test
    fun `check states of view model after data emittied `() {

        viewModel.getCurrency()

        when (val state = viewModel.stateLiveData.getOrAwaitValue()) {
            is DefaultState -> {
                MatcherAssert.assertThat(state.ratesList, CoreMatchers.`is`(emptyList()))
                MatcherAssert.assertThat(
                    viewModel.stateLiveData.value?.baseCurrency,
                    (CoreMatchers.`is`("EUR"))
                )
                MatcherAssert.assertThat(
                    viewModel.stateLiveData.value?.baseValue,
                    (CoreMatchers.`is`(100.0))
                )
            }
            is LoadedState -> {
                MatcherAssert.assertThat(state.loadedAllItems, CoreMatchers.`is`(true))
                MatcherAssert.assertThat(state.ratesList, CoreMatchers.`is`(mutableListOf()))
                MatcherAssert.assertThat(
                    viewModel.stateLiveData.value?.baseCurrency,
                    (CoreMatchers.`is`("EUR"))
                )
                MatcherAssert.assertThat(
                    viewModel.stateLiveData.value?.baseValue,
                    (CoreMatchers.`is`(100.0))
                )

            }

            is ErrorState -> {
                MatcherAssert.assertThat(state.loadedAllItems, CoreMatchers.`is`(false))
                MatcherAssert.assertThat(state.ratesList, CoreMatchers.`is`(emptyList()))
            }
        }

    }

    @Test
    fun `check states of view model after setting new currency and value data emittied `() {

        viewModel.getCurrency()
        viewModel.setNewCurrency("USD", 200.0)

        when (val state = viewModel.stateLiveData.getOrAwaitValue()) {

            is LoadedState -> {
                MatcherAssert.assertThat(state.loadedAllItems, CoreMatchers.`is`(true))
                MatcherAssert.assertThat(state.ratesList, CoreMatchers.`is`(mutableListOf()))
                MatcherAssert.assertThat(
                    viewModel.stateLiveData.value?.baseCurrency,
                    (CoreMatchers.`is`("USD"))
                )
                MatcherAssert.assertThat(
                    viewModel.stateLiveData.value?.baseValue,
                    (CoreMatchers.`is`(200.0))
                )

            }

            is ErrorState -> {
                MatcherAssert.assertThat(state.loadedAllItems, CoreMatchers.`is`(false))
                MatcherAssert.assertThat(state.ratesList, CoreMatchers.`is`(emptyList()))
            }
        }

    }

    @Test
    fun `check states of view model after setting new value for same currency datas calculated  `() {

        viewModel.getCurrency()
        viewModel.setNewCurrencyValue(200.0)

        when (val state = viewModel.stateLiveData.getOrAwaitValue()) {
            is DefaultState -> {
                MatcherAssert.assertThat(state.ratesList, CoreMatchers.`is`(emptyList()))
                MatcherAssert.assertThat(
                    viewModel.stateLiveData.value?.baseCurrency,
                    (CoreMatchers.`is`("EUR"))
                )
                MatcherAssert.assertThat(
                    viewModel.stateLiveData.value?.baseValue,
                    (CoreMatchers.`is`(100.0))
                )
            }
            is RefreshValueListState -> {
                MatcherAssert.assertThat(state.ratesList, CoreMatchers.`is`(mutableListOf()))
                MatcherAssert.assertThat(
                    viewModel.stateLiveData.value?.baseCurrency,
                    (CoreMatchers.`is`("EUR"))
                )
                MatcherAssert.assertThat(
                    viewModel.stateLiveData.value?.baseValue,
                    (CoreMatchers.`is`(200.0))
                )
            }
            is ErrorState -> {
                MatcherAssert.assertThat(state.loadedAllItems, CoreMatchers.`is`(false))
                MatcherAssert.assertThat(state.ratesList, CoreMatchers.`is`(emptyList()))
            }
        }

    }

    @Test
    fun `check states of view model after setting null currency and value data emittied `() {

        viewModel.getCurrency()
        viewModel.setNewCurrency("", 200.0)

        when (val state = viewModel.stateLiveData.getOrAwaitValue()) {

            is ErrorState -> {
                MatcherAssert.assertThat(state.loadedAllItems, CoreMatchers.`is`(false))
                MatcherAssert.assertThat(state.ratesList, CoreMatchers.`is`(emptyList()))
            }
        }

    }

}