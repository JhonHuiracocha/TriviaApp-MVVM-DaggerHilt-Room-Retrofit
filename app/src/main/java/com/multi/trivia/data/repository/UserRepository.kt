package com.multi.trivia.data.repository

import com.multi.trivia.data.database.UserDao
import com.multi.trivia.data.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    fun login(email: String, password: String) = userDao.login(email, password)

    suspend fun register(user: User) = userDao.insert(user)

    suspend fun update(user: User) = userDao.update(user)

    suspend fun delete(userId: Long) = userDao.delete(userId)

}