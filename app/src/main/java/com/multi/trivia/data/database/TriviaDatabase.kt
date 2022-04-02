package com.multi.trivia.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.multi.trivia.data.database.converter.DateConverter
import com.multi.trivia.data.database.dao.ScoreDao
import com.multi.trivia.data.model.Score

@Database(entities = [Score::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class TriviaDatabase : RoomDatabase() {

    abstract fun scoreDao(): ScoreDao

}