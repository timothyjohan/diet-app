package com.example.diet_app.data

import android.util.Log
import com.example.diet_app.data.source.RegisterResponse
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.data.source.remote.MdpService
import retrofit2.Response

//// versi room
//class DefaultPostRepository(
//    private val dataSource: MdpService,
//    create: MdpService
//){
//    suspend fun getAllPosts(force: Boolean = false, query:String): List<String> {
//        return dataSource.getAutocomplete(query);
//    }

//    suspend fun getPostById(id: Int): Post {
//        return dataSource.postDao().getById(id)
//    }
//
//    suspend fun createPost(post: Post) {
//        val posts = dataSource.postDao().getAll()
//        post.id = if(posts.isNotEmpty()){
//            posts[0].id + 1
//        }
//        else{
//            1
//        }
//        Log.d("DEBUG", post.toString())
//        dataSource.postDao().insert(post)
//    }
//
//    suspend fun updatePost(post: Post) {
//        dataSource.postDao().update(post)
//    }
//
//    suspend fun deletePost(post: Post) {
//        dataSource.postDao().delete(post)
//    }

//}

//// versi retrofit
//class DefaultPostRepository(
//    private val dataSource:MdpService
//){
//
//    suspend fun getAllPosts(force: Boolean = false): List<Post> {
//        return dataSource.getAllPosts()
//    }
//
//    suspend fun getPostById(id: Int): Post {
//        return dataSource.getPostById(id)
//    }
//
//    suspend fun createPost(post: Post) {
//        dataSource.createPost(post)
//    }
//
//    suspend fun updatePost(post: Post) {
//        dataSource.update(post.id, post)
//    }
//
//    suspend fun deletePost(post: Post) {
//        dataSource.delete(post.id)
//    }
//
//}

// versi gabungan
class DefaultPostRepository(
    private val localDataSource:AppDatabase,
    private val remoteDataSource:MdpService
){

    suspend fun getAllPosts(force: Boolean = false, query:String): List<String> {
        return remoteDataSource.getAutocomplete(query);
    }

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