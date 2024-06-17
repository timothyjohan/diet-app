package com.example.diet_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.diet_app.data.Config
import com.example.diet_app.data.CurrentUser
import com.example.diet_app.data.User
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//import com.example.diet_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}