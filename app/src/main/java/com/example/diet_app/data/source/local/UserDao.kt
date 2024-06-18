package com.example.diet_app.data.source.local

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.diet_app.data.User

@Dao
interface UserDao {
    @Query("select * from users where email=:email and password=:password")
    fun validUser(email:String, password:String): User?

    @Query("select * from users where email=:email")
    fun getUser(email:String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): User?

    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)
//
//    @Update
//    fun update(post: Post)
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