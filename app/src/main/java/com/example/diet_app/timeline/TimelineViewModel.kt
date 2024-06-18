package com.example.diet_app.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diet_app.SosmedApplication
import com.example.diet_app.data.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimelineViewModel:ViewModel() {
    private val postRepository = SosmedApplication.postRepository
    private val _posts = MutableLiveData<List<Post>>(listOf())
    private val ioScope:CoroutineScope = CoroutineScope(Dispatchers.IO)

    val posts:LiveData<List<Post>>
        get() = _posts

//    fun getPosts(force:Boolean = false){
//        ioScope.launch {
//            _posts.postValue(postRepository.getAllPosts(force))
//        }
//    }
//
//    fun deletePost(post:Post){
//        ioScope.launch {
//            postRepository.deletePost(post)
//            getPosts()
//        }
//    }
}