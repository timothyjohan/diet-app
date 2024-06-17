package com.example.diet_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "config")
data class Config(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
//    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "dailyReminder") val dailyReminder: Boolean,
    @ColumnInfo(name = "weeklyReminder") val weeklyReminder: Boolean
)