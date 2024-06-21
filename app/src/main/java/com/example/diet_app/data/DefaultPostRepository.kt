package com.example.diet_app.data

import com.example.diet_app.data.source.NutritionResponse
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.data.source.remote.MdpService
import retrofit2.Response

class DefaultPostRepository(
    private val localDataSource:AppDatabase,
    private val remoteDataSource:MdpService
){

    suspend fun getAllPosts(force: Boolean = false, query:String): List<String> {
        return remoteDataSource.getAutocomplete(query);
    }

    suspend fun getUser(email:String): CurrentUser {
        return remoteDataSource.getUser(email);
    }

    suspend fun deleteUser(email:String): CurrentUser {
        return remoteDataSource.deleteUser(email);
    }
//    @GET("user/getUser")
//    suspend fun getUser(@Query("email")email:String):Response<String>

//    suspend fun getPostById(id: Int): Post {
//        return localDataSource.postDao().getById(id)
//    }
//
    suspend fun userLogin(loginRequest: LoginRequest):Response<String> {
        return remoteDataSource.userLogin(loginRequest)
    }

    suspend fun addUser(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return remoteDataSource.addUser(registerRequest)
    }


    suspend fun getDates(calendarRequest:CalendarRequest): Response<CalendarResponse> {
        return remoteDataSource.getDates(calendarRequest)
    }
    suspend fun getDatesReport(calendarRequest:CalendarRequest): Response<List<String>> {
        return remoteDataSource.getDatesReport(calendarRequest)
    }
    suspend fun getNutritions(q:String): NutritionResponse {
        return remoteDataSource.getNutritions(q)
    }

//    suspend fun addUser(email:String, password:String, name:String,gender:Boolean, ):Response<RegisterRequest> {
//        val registerRequest = RegisterRequest(email, password, name, gender)
//        return remoteDataSource.addUser(registerRequest)
//    }

//
//    suspend fun updatePost(post: Post) {
//        remoteDataSource.update(post.id, post)
//        localDataSource.postDao().update(post)
//    }
//
//    suspend fun deletePost(post: Post) {
//        remoteDataSource.delete(post.id)
//        localDataSource.postDao().delete(post)
//    }

}