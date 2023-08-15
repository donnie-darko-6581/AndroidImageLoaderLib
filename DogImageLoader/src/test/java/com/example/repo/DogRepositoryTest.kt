package com.example.repo

import com.example.db.DogDao
import com.example.dogimageloader.api.DogApiClient
import com.example.dogimageloader.api.response.MultipleDogs
import com.example.dogimageloader.api.response.SingleDog
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DogRepositoryTest {

    @Mock
    internal lateinit var dogApiClient: DogApiClient

    @Mock
    internal lateinit var dogDao: DogDao

    private lateinit var dogRepository: DogRepository

    @Test
    fun `test getRandomDogImages with data in DB`() = runBlocking {
        val dbData = listOf("url1", "url2")
        val count = 2

        `when`(dogDao.getAllDogs(count)).thenReturn(dbData)
        `when`(dogDao.count()).thenReturn(count)

        val result = dogRepository.getRandomDogImages(count)

        assertEquals(dbData, result)
    }

    @Test
    fun `test getRandomDogImages with partial data in DB`() = runBlocking {
        val dbData = listOf("url1", "url2")
        val count = 3
        val remainingCount = count - dbData.size
        val status = "success"

        `when`(dogDao.getAllDogs(count)).thenReturn(dbData)
        `when`(dogDao.count()).thenReturn(dbData.size)
        `when`(dogApiClient.multipleDogs(remainingCount))
            .thenReturn(MultipleDogs(status = status, imageUrls = listOf("url3", "url4")))

        val result = dogRepository.getRandomDogImages(count)

        assertEquals(dbData + listOf("url3", "url4"), result)
    }

    @Test
    fun `test getRandomDogImages with no data in DB`() = runBlocking {
        val count = 3
        val status = "success"

        `when`(dogDao.getAllDogs(count)).thenReturn(emptyList())
        `when`(dogDao.count()).thenReturn(0)
        `when`(dogApiClient.multipleDogs(count))
            .thenReturn(MultipleDogs(status = status, imageUrls = listOf("url1", "url2", "url3")))

        val result = dogRepository.getRandomDogImages(count)

        assertEquals(listOf("url1", "url2", "url3"), result)
        verify(dogDao, times(1)).insertAll(anyList())
    }

    @Test
    fun `test getSingleDogImage with data in DB`() = runBlocking {
        val pageNo = 1L
        val imageUrl = "url1"
        val dbData = listOf(imageUrl)

        `when`(dogDao.getDog(pageNo)).thenReturn(dbData)

        val result = dogRepository.getSingleDogImage(pageNo)

        assertEquals(imageUrl, result)
    }

    @Test
    fun `test getSingleDogImage with no data in DB`() = runBlocking {
        val pageNo = 1L
        val imageUrl = "url1"
        val status = "success"

        `when`(dogDao.getDog(pageNo)).thenReturn(emptyList())
        `when`(dogApiClient.singleDog())
            .thenReturn(SingleDog(status = status, imageUrl = imageUrl))

        val result = dogRepository.getSingleDogImage(pageNo)

        assertEquals(imageUrl, result)
        verify(dogDao, times(1)).insert(any())
    }
}