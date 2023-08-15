package com.example.repo

import android.util.Log
import com.example.db.DogDao
import com.example.db.DogEntity
import com.example.dogimageloader.api.DogApiClient

class DogRepository(
    private val dogApiClient: DogApiClient,
    private val dogDao: DogDao
) {
    /**
     * Method which tries to get images from db, if they are lacking use api to download
     * If there is nothing in DB, we fetch from api
     */
    suspend fun getRandomDogImages(count: Int): List<String> {
        val dogEntitiesFromDb = dogDao.getAllDogs(count = count)
        val dbCount = dogDao.count()

        // If data exists in the database, return it
        if (dogEntitiesFromDb.isNotEmpty() && count <= dbCount) {
            return dogEntitiesFromDb
        }

        // If the database is lacking, fetch data from the API
        val remainingCount = count - dbCount
        val dogApiResponse = dogApiClient.multipleDogs(remainingCount)

        // Save the API response to the database
        dogDao.insertAll(
            dogApiResponse.imageUrls.mapIndexed { index, url ->
                DogEntity(
                    id = dbCount.toLong() + index + 1,
                    imageUrl = url
                )
            }
        )

        // Return the fetched data
        return dogEntitiesFromDb + dogApiResponse.imageUrls
    }

    /**
     * Get single image from db
     */
    suspend fun getSingleDogImage(pageNo: Long): String {
        val dogEntitiesFromDb = dogDao.getDog(pageNo = pageNo)

        // If data exists in the database, return a random item
        if (dogEntitiesFromDb.isNotEmpty()) {
            Log.i("DogRepository", "single dog image $pageNo")
            return dogEntitiesFromDb[0]
        }

        // If the database is empty, fetch data from the API
        val dogApiResponse = dogApiClient.singleDog()
        val dogEntity = DogEntity(id = pageNo, imageUrl = dogApiResponse.imageUrl)
        // Save in db
        dogDao.insert(dogEntity)
        Log.i("DogRepository", "single dog image from api, total count: " + dogDao.count())

        return dogApiResponse.imageUrl
    }


}