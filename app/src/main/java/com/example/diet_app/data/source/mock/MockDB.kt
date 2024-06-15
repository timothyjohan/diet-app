package  com.example.diet_app.data.source.mock

import  com.example.diet_app.data.Post
object MockDB{
    private val posts:ArrayList<Post> = ArrayList()
    init{
        for(i in 1..5){
            posts.add(Post(i, "Title $i", "Content $i"))
        }
    }
    fun getAllPosts(force:Boolean = false):ArrayList<Post>{
        return ArrayList(posts)
    }
    fun getPostById(id:Int):Post?{
        return posts.find {
            it.id == id
        }
    }
    fun createPost(post:Post){
        posts.add(post)
    }
    fun updatePost(post: Post){
        val p = posts.find {
            it.id == post.id
        }
        p?.title = post.title
        p?.content = post.content
    }
    fun deletePost(post:Post){
        val idx = posts.indexOfFirst {
            it.id == post.id
        }
        if(idx >= 0){
            posts.removeAt(idx)
        }
    }
}