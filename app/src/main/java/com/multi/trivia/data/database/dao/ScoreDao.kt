package com.multi.trivia.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.multi.trivia.data.model.Score

@Dao
abstract class ScoreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(score: Score): Long

    @Query("SELECT * FROM scores ORDER BY date")
    abstract fun getScores(): LiveData<List<Score>>

}