package com.multi.trivia.data.repository

import com.multi.trivia.data.database.dao.ScoreDao
import com.multi.trivia.data.model.Score
import javax.inject.Inject

class ScoreRepository @Inject constructor(
    private val scoreDao: ScoreDao
) {

    suspend fun insert(score: Score) = scoreDao.insert(score)

    fun getScores() = scoreDao.getScores()

}