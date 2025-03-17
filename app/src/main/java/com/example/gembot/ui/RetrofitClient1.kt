package com.example.gembot.ui

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient1 {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    val instance: GeminiApiService1 by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeminiApiService1::class.java)
    }
}