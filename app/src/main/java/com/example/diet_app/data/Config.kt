package com.example.diet_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config")
data class Config(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "dailyReminder") val dailyReminder: Boolean,
    @ColumnInfo(name = "weeklyReminder") val weeklyReminder: Boolean
)