package com.example.diet_app.data

import java.time.LocalDate
import java.util.Date

class CalendarRequest(
    val email:String,
    val before:Int,
    val after:LocalDate,
) {
}