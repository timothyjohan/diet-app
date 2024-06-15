package com.example.diet_app.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diet_app.SosmedApplication
import com.example.diet_app.data.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel:ViewModel() {
    private val postRepository = SosmedApplication.postRepository
    private val _post = MutableLiveData<Post?>(null)
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    val post: LiveData<Post?>
        get() = _post

    fun getPost(id:Int){
        ioScope.launch {
            _post.postValue(postRepository.getPostById(id))
        }
    }

    fun createPost(title:String, content:String){
        ioScope.launch {
            postRepository.createPost(Post(0, title, content))
        }
    }

    fun updatePost(title:String, content:String){
        ioScope.launch {
            if(post.value != null){
                postRepository.updatePost(Post(post.value!!.id, title, content))
            }
        }
    }

}