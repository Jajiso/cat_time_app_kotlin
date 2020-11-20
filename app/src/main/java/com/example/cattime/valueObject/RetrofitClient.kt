package com.example.cattime.valueObject

import com.example.cattime.domain.WebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val webservice by lazy {
        Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebService::class.java)
    }
}