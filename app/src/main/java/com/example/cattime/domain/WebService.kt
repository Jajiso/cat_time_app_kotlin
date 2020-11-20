package com.example.cattime.domain

import com.example.cattime.data.model.Cat
import com.example.cattime.data.model.CatImage
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("breeds")
    suspend fun getBreedsList(): List<Cat>

    @GET("images/search")
    suspend fun getCatImage(@Query("breed_id") id: String): List<CatImage>
}