package com.example.api

import com.example.dogimageloader.api.DogApiClient
import com.example.dogimageloader.api.DogApiService
import com.example.dogimageloader.api.response.MultipleDogs
import com.example.dogimageloader.api.response.SingleDog
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

// TODO : We can use Testing lib for coroutines, this will block test thread, avoiding for simplicity
class DogApiClientTest {

    private val mockApiService: DogApiService = mock(DogApiService::class.java)

    private val testDispatcher = StandardTestDispatcher()
    private val dogApiClient = DogApiClient(mockApiService, testDispatcher)


    @Test
    fun `test generate random dog success`() {

        val imageUrl = "https://some-dog-image"
        val status = "success"
        val mockResponse = SingleDog(imageUrl = imageUrl, status = status)
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

    @Test
    fun testGetRandomDogImages_Success() = runBlocking {
        val imageUrls = listOf("https://example.com/dog1.jpg", "https://example.com/dog2.jpg")
        val status = "success"
        val mockResponse = MultipleDogs(imageUrls = imageUrls, status = status)
        `when`(mockApiService.getRandomDogImages(2)).thenReturn(mockResponse)

        val result = dogApiClient.multipleDogs(2)

        assertEquals(imageUrls.size, result.imageUrls.size)
        assertEquals(imageUrls[0], result.imageUrls[0])
        assertEquals(imageUrls[1], result.imageUrls[1])
    }

}