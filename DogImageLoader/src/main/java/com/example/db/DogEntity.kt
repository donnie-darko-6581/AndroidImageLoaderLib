package com.example.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogImages")
data class DogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Add an ID field for unique identification
    val imageUrl: String
)