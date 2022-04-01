package com.multi.trivia.data.repository

import com.multi.trivia.data.network.TriviaService
import javax.inject.Inject

class TriviaRepository @Inject constructor(
    private val triviaService: TriviaService
) {

    suspend fun fetchQuiz(amount: Int, category: Long, difficulty: String, type: String) =
        triviaService.fetchQuiz(amount, category, difficulty, type)

}