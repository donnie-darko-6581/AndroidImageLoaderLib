package com.example.dogimageloader.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class SingleDog(
    @Json(name = "message")
    val imageUrl: String,
    @Json(name = "status")
    val status: String
)