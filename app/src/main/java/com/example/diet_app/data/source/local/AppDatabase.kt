package com.example.diet_app.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.diet_app.data.Post

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun postDao(): PostDao
}