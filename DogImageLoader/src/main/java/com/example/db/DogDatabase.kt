package com.example.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DogEntity::class], version = 1, exportSchema = false)
internal abstract class DogDatabase : RoomDatabase() {
    abstract fun dogDao(): DogDao
}