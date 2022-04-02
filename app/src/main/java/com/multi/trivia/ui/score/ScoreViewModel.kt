package com.multi.trivia.ui.score

import androidx.lifecycle.ViewModel
import com.multi.trivia.data.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
) : ViewModel() {

    fun getScores() = scoreRepository.getScores()

}