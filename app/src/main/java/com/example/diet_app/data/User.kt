package com.example.diet_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "email") var email:String,
    @ColumnInfo(name = "password") var password:String,
    @ColumnInfo(name = "name") var name:String,
    @ColumnInfo(name = "gender") var gender:Boolean
)