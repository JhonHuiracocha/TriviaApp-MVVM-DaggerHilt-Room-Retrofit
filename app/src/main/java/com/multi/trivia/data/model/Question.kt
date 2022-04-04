package com.multi.trivia.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    @SerializedName("category") val category: String,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("question") var question: String,
    @SerializedName("correct_answer") var correctAnswer: String,
    @SerializedName("incorrect_answers") var incorrectAnswers: List<String>
) : Parcelable
