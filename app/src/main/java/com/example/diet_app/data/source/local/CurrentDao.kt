package com.example.diet_app.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.diet_app.data.CurrentUser

@Dao
interface CurrentDao {
    @Query("select * from current limit 1")
    fun getUser(): CurrentUser?
//    @Query("select * from users where email=:email")
//    fun getUser(email:String): User?
    @Insert
    fun insert(current: CurrentUser)
    @Delete
    fun delete(current: CurrentUser)
    @Update
    fun update(current: CurrentUser)
//
//    @Delete
//    fun delete(post: Post)
//
//    @Query("delete from posts")
//    fun clearDb()
//
//    @Insert
//    fun insertMany(posts:List<Post>)
}