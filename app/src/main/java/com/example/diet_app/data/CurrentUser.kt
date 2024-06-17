package com.example.diet_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "current")
data class CurrentUser (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name = "email") val email:String,
    @ColumnInfo(name = "password") val password:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "gender") val gender:Boolean
)