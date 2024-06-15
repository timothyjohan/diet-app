package com.example.diet_app.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.diet_app.data.Post

@Dao
interface PostDao {
    @Query("select * from posts order by id desc")
    fun getAll(): List<Post>

    @Query("select * from posts where id=:id")
    fun getById(id:Int): Post

    @Insert
    fun insert(post: Post)

    @Update
    fun update(post: Post)

    @Delete
    fun delete(post: Post)

    @Query("delete from posts")
    fun clearDb()

    @Insert
    fun insertMany(posts:List<Post>)
}