package com.example.diet_app.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    val weight = MutableLiveData<Double>()
    val height = MutableLiveData<Double>()
    val tdeeResult = MutableLiveData<Double>()

    fun calculateTDEE(weight: Double, height: Double, gender: Boolean, activityLevel: String) {
        val bmr = if (gender) {
            88.362 + (13.397 * weight) + (4.799 * height)
        } else {
            447.593 + (9.247 * weight) + (3.098 * height)
        }

        val tdee = when (activityLevel) {
            "Sedentary (office job)" -> bmr * 1.2
            "Light Exercise (1-2 days/week)" -> bmr * 1.375
            "Moderate Exercise (3-5 days/week)" -> bmr * 1.55
            "Heavy Exercise (6-7 days/week)" -> bmr * 1.725
            "Athlete (2x per day)" -> bmr * 1.9
            else -> bmr
        }

        tdeeResult.value = tdee
    }
}