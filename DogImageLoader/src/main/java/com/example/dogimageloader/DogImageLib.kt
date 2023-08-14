package com.example.dogimageloader

class DogImageLib: ImageLibBootStrap {

    private var lib: DogImageLib? = null
    private var policy: DogImageLibPolicy? = null

    override fun init(policy: DogImageLibPolicy?) {
        synchronized(this) {
            if (lib == null) {
                createInstance(policy)
                // fetch initial images
            }
        }
    }

    private fun createInstance(policy: DogImageLibPolicy?): DogImageLib {
        lib = DogImageLib()
        this.policy = policy ?: DogImageLibPolicy.optimum()
        return lib!!
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
    fun getImage(): String
    fun getImages(count: Int): List<String>
    fun getNextImage(): String
    fun getPreviousImage() : String
}

interface ImageLibBootStrap {
    fun init(policy: DogImageLibPolicy?)
}