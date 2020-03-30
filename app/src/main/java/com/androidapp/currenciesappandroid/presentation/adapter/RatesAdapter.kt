package com.androidapp.currenciesappandroid.presentation.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.currenciesappandroid.R
import com.androidapp.currenciesappandroid.domain.model.CurrencyRate
import com.androidapp.currenciesappandroid.presentation.rx.RxBus
import com.androidapp.currenciesappandroid.presentation.util.getCurrencyDescription
import com.androidapp.currenciesappandroid.presentation.util.getCurrencyFlag
import com.androidapp.currenciesappandroid.presentation.util.toCurrencyValue
import kotlinx.android.synthetic.main.item_rate.view.*


/**
 * Created by S.Nur Uysal on 2020-03-24.
 */

class RatesAdapter(
    private var ratesList: List<CurrencyRate>
) :
    RecyclerView.Adapter<RatesAdapter.ModelViewHolder>() {

    private val valueWatcher: TextWatcher

    private var baseCurrency: String = ratesList[0].currency
    private var baseValue: Double = ratesList[0].value

    fun dispatchListUpdate(newRatesList: List<CurrencyRate>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(newRatesList, ratesList))
        ratesList = newRatesList
        diffResult.dispatchUpdatesTo(this)
    }

    class ModelViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    init {

        this.valueWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(newValue: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(newValue: Editable?) {
                if (newValue?.length!! > 0) {
                    val strValue: String = newValue.toString().trim()
                    val newCurrencyValue = strValue.toCurrencyValue()
                    if (newCurrencyValue != baseValue) {
                        baseValue = newCurrencyValue
                        ratesList[0].value = baseValue
                        RxBus.publish(CurrencyValueChangedEvent(baseValue))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ModelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rate, parent, false)
        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        val currencyRate = ratesList[position]

        val newValue: String = "%.2f".format(currencyRate.value)

        if (holder.view.et_rate.text.toString() != newValue) {
            holder.view.et_rate.setText(newValue)
        }


        holder.view.tv_currency_name.text = currencyRate.currency
        holder.view.tv_currency_desc.text =
            currencyRate.currency.getCurrencyDescription(holder.view.context)

        val flagDrawable = ResourcesCompat.getDrawable(
            holder.view.iv_currency.resources,
            currencyRate.currency.getCurrencyFlag(holder.view.context),
            null
        )

        holder.view.iv_currency.setImageDrawable(flagDrawable)

        holder.view.setOnClickListener {
            if (position != RecyclerView.NO_POSITION && position > 0 && baseCurrency != currencyRate.currency) {
                baseCurrency = currencyRate.currency
                baseValue = currencyRate.value
                RxBus.publish(CurrencySelectEvent(baseCurrency, baseValue))
            }
        }

        holder.view.et_rate.isEnabled = position == 0

        holder.view.et_rate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && position == 0) {
                holder.view.et_rate.addTextChangedListener(valueWatcher)

            } else {
                holder.view.et_rate.removeTextChangedListener(valueWatcher)
            }
        }


    }


    override fun getItemCount() = ratesList.size

    private class DiffUtilCallback(
        val newRatesList: List<CurrencyRate>,
        val oldList: List<CurrencyRate>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newRatesList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].rate == newRatesList[newItemPosition].rate
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].value == newRatesList[newItemPosition].value
        }
    }
}
