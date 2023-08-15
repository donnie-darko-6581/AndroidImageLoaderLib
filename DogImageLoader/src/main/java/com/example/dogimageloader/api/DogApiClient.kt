package com.example.dogimageloader.api

import com.example.dogimageloader.api.response.MultipleDogs
import com.example.dogimageloader.api.response.SingleDog
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal open class DogApiClient constructor(
    private val apiService: DogApiService,
    private val context: CoroutineContext
) {
    suspend fun singleDog(): SingleDog {
        return withContext(context) {
            apiService.getRandomDogImage()
        }
    }

    suspend fun multipleDogs(count: Int): MultipleDogs {
        return withContext(context) {
            apiService.getRandomDogImages(count = count)
        }
    }
}