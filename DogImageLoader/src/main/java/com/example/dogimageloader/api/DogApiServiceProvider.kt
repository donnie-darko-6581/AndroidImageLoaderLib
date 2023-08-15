package com.example.dogimageloader.api

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal object DogApiServiceProvider {

    private const val BASE_URL = "https://dog.ceo/api/"

    private var dogApiService: DogApiService? = null

    fun service(): DogApiService {
        synchronized(this) {
            dogApiService?.let {
                return it
            }

            val moshi = Moshi.Builder()
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

            dogApiService = retrofit.create(DogApiService::class.java)
            return dogApiService!!
        }
    }
}