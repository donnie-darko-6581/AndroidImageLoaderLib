package com.example.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dogs: List<DogEntity>)

    @Query("SELECT imageUrl FROM dogImages LIMIT :count")
    suspend fun getAllDogs(count: Int): List<String>

    @Query("SELECT COUNT(*) FROM dogImages")
    suspend fun count(): Int
}