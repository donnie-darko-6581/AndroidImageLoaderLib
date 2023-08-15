package com.example.db

import android.content.Context
import androidx.room.Room

internal object DogDbSingleton {
    private var instance: DogDatabase? = null

    fun instance(context: Context): DogDatabase {
        synchronized(this) {
            return instance ?: buildDatabase(context).also { instance = it }
        }
    }

    private fun buildDatabase(context: Context): DogDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DogDatabase::class.java,
            "dog-db"
        ).build()
    }

    fun getDogDao(context: Context): DogDao {
        return instance(context).dogDao()
    }
}