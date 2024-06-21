package com.example.diet_app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.diet_app.data.DefaultPostRepository
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.data.source.remote.MdpService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SosmedApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        initRepository(baseContext)
    }

    companion object{
        lateinit var postRepository: DefaultPostRepository

        fun initRepository(context:Context){
////            context.deleteDatabase("DBCalorieCraft")
            val roomDb = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "DBCalorieCraft"
            ).build()
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val lenientMoshiConverterFactory = MoshiConverterFactory.create(moshi).asLenient()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(lenientMoshiConverterFactory)
                .baseUrl("http://chisel-southern-father.glitch.me/api/")
                .build()

            postRepository = DefaultPostRepository(
                roomDb,
                retrofit.create(MdpService::class.java)
            )
        }
    }
}