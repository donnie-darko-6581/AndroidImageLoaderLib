package com.example.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogImages")
internal data class DogEntity(
    @PrimaryKey
    val id: Long = 1, // Add an ID field for unique identification
    val imageUrl: String
)