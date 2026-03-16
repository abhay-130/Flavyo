package com.example.flavyo

import com.example.flavyo.data.IceCreamData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface IceCreamApiService {
    @GET("macros/s/AKfycbyLmXgNhLMRNp4pMSJk73CApfjDrqrm3sVfLY-YN5oAMcrFwXPtLHkr_cvvuhhzC3vbxA/exec")
    suspend fun getIceCreams(): List<IceCreamData>
}

object RetrofitClient {
    private const val BASE_URL = "https://script.google.com/"

    val instance: IceCreamApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IceCreamApiService::class.java)
    }
}
