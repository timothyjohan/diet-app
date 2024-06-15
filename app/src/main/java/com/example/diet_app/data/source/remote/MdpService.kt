package com.example.diet_app.data.source.remote

import com.example.diet_app.data.Post
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MdpService {
    @GET("posts")
    suspend fun getAllPosts(@Query("q") q:String = ""):List<Post>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id:Int):Post

    @POST("posts")
    suspend fun createPost(@Body post:Post):Post

    @PUT("posts/{id}")
    suspend fun update(@Path("id") id:Int, @Body post:Post):Post

    @DELETE("posts/{id}")
    suspend fun delete(@Path("id") id:Int):Post
}