package com.example.dogimageloader.api

import com.example.dogimageloader.api.response.SingleDog
import retrofit2.http.GET

interface DogApiService {

    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): SingleDog
}