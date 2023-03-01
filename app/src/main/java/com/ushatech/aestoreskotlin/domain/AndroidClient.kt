package com.ushatech.aestoreskotlin.domain

import com.ushatech.aestoreskotlin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AndroidClient {

    val AE_STORES_LIVE_URL = "https://aestores.online/" // When running in the local host.


    val retrofitClient: Retrofit.Builder by lazy {
        val levelType = HttpLoggingInterceptor.Level.BODY
        val loggingInterceptor= BasicAuthInterceptor()
        val debugInterceptor=HttpLoggingInterceptor().setLevel(levelType)
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.addInterceptor(debugInterceptor)

        Retrofit.Builder().baseUrl(AE_STORES_LIVE_URL).client(okHttpClient.build()).addConverterFactory(
            GsonConverterFactory.create())
    }

    val apiInterface:ApiService by lazy {
        retrofitClient.build().create(ApiService::class.java)
    }
}