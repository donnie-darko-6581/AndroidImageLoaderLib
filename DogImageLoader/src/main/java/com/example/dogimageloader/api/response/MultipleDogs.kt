package com.example.dogimageloader.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class MultipleDogs(
    @Json(name = "message")
    val imageUrls: List<String>,
    @Json(name = "status")
    val status: String
)