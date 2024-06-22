package com.example.diet_app.data

import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class CalendarResponse(
    val _id:String,
    val email:String,
    val food_name:String,
    val calories:Double,
    val proteins: Double,
    val fats: Double,
    val carbs:Double,
    val date:String,
    val __v:Int
) {
}