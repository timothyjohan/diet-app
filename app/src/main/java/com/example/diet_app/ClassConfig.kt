package com.example.diet_app

import android.content.Context
import android.content.SharedPreferences

class ClassConfig(private val context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)

    var dailyReminder: Boolean
        get() = preferences.getBoolean("dailyReminder", false)
        set(value) = preferences.edit().putBoolean("dailyReminder", value).apply()

    var weeklyReminder: Boolean
        get() = preferences.getBoolean("weeklyReminder", false)
        set(value) = preferences.edit().putBoolean("weeklyReminder", value).apply()
}
