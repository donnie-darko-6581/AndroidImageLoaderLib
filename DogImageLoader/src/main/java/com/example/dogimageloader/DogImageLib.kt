package com.example.dogimageloader

import android.content.Context
import com.example.db.DogDbSingleton
import com.example.dogimageloader.api.DogApiClient
import com.example.dogimageloader.api.DogApiServiceProvider
import com.example.repo.DogRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DogImageLib private constructor() : ImageLibMethods {

    private var dogRepo: DogRepository? = null

    companion object {
        private var lib: DogImageLib? = null
        private var policy: DogImageLibPolicy? = null

        // we need to maintain state to have functions like prev/next image
        private var pageNo: Int = 1

        fun init(
            context: Context,
            policy: DogImageLibPolicy = DogImageLibPolicy.optimum()
        ) {
            synchronized(this) {
                if (lib == null) {
                    createInstance(policy = policy)
                    load(context = context)
                }
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        private fun load(context: Context) {
            lib!!.dogRepo = DogRepository(
                dogApiClient = DogApiClient(apiService = DogApiServiceProvider.service()),
                dogDao = DogDbSingleton.instance(context = context).dogDao()
            )
            GlobalScope.launch {
                // loading first image separately for smooth ui
                val image = lib!!.dogRepo!!.getSingleDogImage(pageNo = pageNo)

                // load next few images
                if (image != null) {
                    // make sure first call is successful
                    lib!!.dogRepo!!.getRandomDogImages(count = policy!!.prefetchCount)
                }
            }
        }

        private fun createInstance(policy: DogImageLibPolicy): DogImageLib {
            lib = DogImageLib()
            this.policy = policy
            return lib!!
        }

        fun getInstance(): DogImageLib {
            lib?.let {
                return it
            } ?: throw Exception("Trying to use Dog lib without initialisation")
        }
    }

    override suspend fun getImage(): String {
        return lib!!.dogRepo!!.getSingleDogImage(pageNo = pageNo)
    }

    override suspend fun getImages(count: Int): List<String> {
        return lib!!.dogRepo!!.getRandomDogImages(count = count)
    }

    override suspend fun getNextImage(): String {
        pageNo++
        return lib!!.dogRepo!!.getSingleDogImage(pageNo = pageNo)
    }

    override suspend fun getPreviousImage(): String {
        if (pageNo > 1) {
            pageNo--
        }
        return lib!!.dogRepo!!.getSingleDogImage(pageNo = pageNo)
    }

}

/**
 * Configuration class for lib behaviour, can be setup based on nw conditions
 *
 * 1. Lib will always fetch first image in single api call
 * 2. Lib will bulk prefetch next few images and save
 *
 */
class DogImageLibPolicy(
    val prefetchCount: Int,
    val fetchSize: Int
) {
   companion object {
       fun aggressive(): DogImageLibPolicy {
           return DogImageLibPolicy(prefetchCount = 3, fetchSize = 3)
       }
       fun optimum(): DogImageLibPolicy {
           return DogImageLibPolicy(prefetchCount = 3, fetchSize = 2)
       }

       fun tight(): DogImageLibPolicy {
           return DogImageLibPolicy(prefetchCount = 1, fetchSize = 1)
       }

       fun custom(
           prefetchCount: Int,
           fetchSize: Int
       ): DogImageLibPolicy {
           return DogImageLibPolicy(prefetchCount = prefetchCount, fetchSize = fetchSize)
       }
   }
}

interface ImageLibMethods {
    suspend fun getImage(): String
    suspend fun getImages(count: Int): List<String>
    suspend fun getNextImage(): String
    suspend fun getPreviousImage() : String
}