package com.example.dogimageloader.api

import com.example.dogimageloader.api.response.MultipleDogs
import com.example.dogimageloader.api.response.SingleDog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class DogApiClient(private val apiService: DogApiService) {

    suspend fun singleDog(): SingleDog {
        return withContext(Dispatchers.IO) {
            apiService.getRandomDogImage()
        }
    }

    suspend fun multipleDogs(count: Int): MultipleDogs {
        return withContext(Dispatchers.IO) {
            apiService.getRandomDogImages(count = count)
        }
    }
}