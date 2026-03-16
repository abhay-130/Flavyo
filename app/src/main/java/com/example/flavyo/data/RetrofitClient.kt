package com.example.flavyo.data

import com.example.flavyo.IceCreamApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://script.google.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val iceCreamApi: IceCreamApiService by lazy {
        retrofit.create(IceCreamApiService::class.java)
    }

    val authApi: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
}
