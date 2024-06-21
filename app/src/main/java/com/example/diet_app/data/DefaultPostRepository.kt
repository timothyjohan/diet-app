package com.example.diet_app.data

import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.data.source.remote.MdpService
import retrofit2.Response
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    suspend fun updateCalories(email:String, calories:Int): String {
        return remoteDataSource.updateCalories(email, calories);
    }

//    @PUT("calories/{email}")
//    suspend fun updateCalories(@Path("email")email:String, @Query("calories") calories:String): CurrentUser
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