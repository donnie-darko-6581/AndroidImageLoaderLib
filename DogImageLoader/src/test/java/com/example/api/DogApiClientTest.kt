package com.example.api

import com.example.dogimageloader.api.DogApiClient
import com.example.dogimageloader.api.DogApiService
import com.example.dogimageloader.api.response.SingleDog
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

// TODO : We can use Testing lib for coroutines, this will block test thread, avoiding for simplicity
class DogApiClientTest {

    private val mockApiService: DogApiService = mock(DogApiService::class.java)
    private val dogApiClient = DogApiClient(mockApiService)

    @Test
    fun `test generate random dog success`() {

        val imageUrl = "https://some-dog-image"
        val mockResponse = SingleDog(imageUrl)
        runBlocking {
            `when`(mockApiService.getRandomDogImage()).thenReturn(mockResponse)
            val result = dogApiClient.singleDog()
            assertEquals(imageUrl, result.imageUrl)
        }
    }

    @Test(expected = Exception::class)
    fun `test generate random dog error`() {
        runBlocking {
            `when`(mockApiService.getRandomDogImage()).thenThrow(Exception("Error"))
            dogApiClient.singleDog()
        }
    }
}