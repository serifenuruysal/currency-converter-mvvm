package com.androidapp.currenciesappandroid.data.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by S.Nur Uysal on 2020-03-21.
 */
private const val API_BASE_URL = "https://hiring.revolut.codes/api/"

class ApiService {

    companion object {
        fun create(): Api {

            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    MoshiConverterFactory.create(
                        Moshi.Builder()
                            .add(KotlinJsonAdapterFactory())
                            .build()
                    )
                )
                .client(providesOkHttpClient())
                .baseUrl(API_BASE_URL)
                .build()
                .create(Api::class.java)
        }

        private fun providesOkHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }
    }


}
