package com.mobile.data.data.remote

import com.mobile.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object Networking {
    var BASE_URL = "https://data.gov.sg/"

    fun createNetworking() : NetworkService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor()
                        .apply {
                            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            else HttpLoggingInterceptor.Level.NONE
                        })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(
                NetworkService::class.java
            )
    }

}