package com.example.diet_app.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.diet_app.data.Config

@Dao
interface ConfigDao {
    @Query("SELECT * FROM config WHERE email = :email")
    suspend fun getConfigByEmail(email: String): Config?

    @Insert
    suspend fun insert(config: Config)

    @Update
    suspend fun update(config: Config)
}