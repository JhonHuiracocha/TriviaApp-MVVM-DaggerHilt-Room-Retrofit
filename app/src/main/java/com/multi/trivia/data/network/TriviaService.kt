package com.multi.trivia.data.network

import com.multi.trivia.data.model.QuizResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService {

    @GET("api.php")
    suspend fun fetchQuiz(
        @Query("amount") amount: Int,
        @Query("category") category: Long,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String,
    ): Response<QuizResponse>

}