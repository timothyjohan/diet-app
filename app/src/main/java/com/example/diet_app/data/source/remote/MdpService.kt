package com.example.diet_app.data.source.remote

import com.example.diet_app.data.LoginRequest
import com.example.diet_app.data.Post
import com.example.diet_app.data.RegisterRequest
import com.example.diet_app.data.source.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MdpService {
    @GET("3rdparty/autocomplete")
    suspend fun getAutocomplete(@Query("q") q:String = ""):List<String>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id:Int):Post

    @POST("user/login")
    suspend fun userLogin(@Body loginRequest: LoginRequest):Response<String>

    @POST("user/add")
    suspend fun addUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @PUT("posts/{id}")
    suspend fun update(@Path("id") id:Int, @Body post:Post):Post

    @DELETE("posts/{id}")
    suspend fun delete(@Path("id") id:Int):Post
}