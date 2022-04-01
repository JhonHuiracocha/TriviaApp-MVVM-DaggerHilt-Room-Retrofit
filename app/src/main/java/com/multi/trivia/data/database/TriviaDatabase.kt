package com.multi.trivia.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.multi.trivia.data.model.User

@Database(entities = [User::class], version = 1)
abstract class TriviaDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}