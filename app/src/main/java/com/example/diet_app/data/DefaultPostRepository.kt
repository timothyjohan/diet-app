package com.example.diet_app.data

import android.util.Log
import com.example.diet_app.data.source.local.AppDatabase
import com.example.diet_app.data.source.remote.MdpService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

//// versi room
class DefaultPostRepository(
    private val dataSource:AppDatabase
){
    suspend fun getAllPosts(force: Boolean = false): List<Post> {
        return dataSource.postDao().getAll()
    }

    suspend fun getPostById(id: Int): Post {
        return dataSource.postDao().getById(id)
    }

    suspend fun createPost(post: Post) {
        val posts = dataSource.postDao().getAll()
        post.id = if(posts.isNotEmpty()){
            posts[0].id + 1
        }
        else{
            1
        }
        Log.d("DEBUG", post.toString())
        dataSource.postDao().insert(post)
    }

    suspend fun updatePost(post: Post) {
        dataSource.postDao().update(post)
    }

    suspend fun deletePost(post: Post) {
        dataSource.postDao().delete(post)
    }

}

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
//class DefaultPostRepository(
//    private val localDataSource:AppDatabase,
//    private val remoteDataSource:MdpService
//){
//
//    suspend fun getAllPosts(force: Boolean = false): List<Post> {
//        if(force){
//            val posts = remoteDataSource.getAllPosts()
//            localDataSource.postDao().clearDb()
//            localDataSource.postDao().insertMany(posts)
//        }
//        return localDataSource.postDao().getAll()
//    }
//
//    suspend fun getPostById(id: Int): Post {
//        return localDataSource.postDao().getById(id)
//    }
//
//    suspend fun createPost(post: Post) {
//        val newPost = remoteDataSource.createPost(post)
//        localDataSource.postDao().insert(newPost)
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

//}