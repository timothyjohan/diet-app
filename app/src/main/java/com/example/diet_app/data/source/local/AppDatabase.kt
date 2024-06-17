package com.example.diet_app.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.diet_app.data.Config
import com.example.diet_app.data.CurrentUser
import com.example.diet_app.data.Post
import com.example.diet_app.data.User

@Database(entities = [Post::class, User::class, Config::class, CurrentUser::class], version = 23)
abstract class AppDatabase:RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun configDao(): ConfigDao
    abstract fun currentDao(): CurrentDao
}