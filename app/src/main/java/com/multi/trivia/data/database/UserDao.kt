package com.multi.trivia.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.multi.trivia.data.model.User

@Dao
abstract class UserDao : BaseDao<User> {

    @Query("SELECT * FROM users WHERE status = 1 AND (email like :email AND password like :password) LIMIT 1")
    abstract fun login(email: String, password: String): LiveData<User>

}