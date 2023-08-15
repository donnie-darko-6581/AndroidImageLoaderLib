package com.example.dogimageloader.api

import com.example.dogimageloader.api.response.MultipleDogs
import com.example.dogimageloader.api.response.SingleDog
import retrofit2.http.GET
import retrofit2.http.Path

internal interface DogApiService {

    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): SingleDog

    @GET("breeds/image/random/{count}")
    suspend fun getRandomDogImages(@Path("count") count: Int): MultipleDogs
}