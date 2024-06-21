package com.example.diet_app.data

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