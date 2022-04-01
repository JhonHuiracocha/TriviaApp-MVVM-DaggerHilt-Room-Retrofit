package com.multi.trivia.data.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: T): Long

    @Update
    suspend fun update(t: T): Int

    @Query("UPDATE users SET status = 0 WHERE user_id = :userId")
    suspend fun delete(userId: Long): Int

}