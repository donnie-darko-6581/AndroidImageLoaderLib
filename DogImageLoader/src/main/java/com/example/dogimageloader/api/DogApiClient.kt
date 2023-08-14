package com.example.dogimageloader.api

import com.example.dogimageloader.api.response.SingleDog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogApiClient(private val apiService: DogApiService) {

    suspend fun singleDog(): SingleDog {
        return withContext(Dispatchers.IO) {
            apiService.getRandomDogImage()
        }
    }
}