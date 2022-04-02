package com.multi.trivia.ui.quiz.results

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multi.trivia.data.model.Score
import com.multi.trivia.data.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
) : ViewModel() {

    val statusRegister: MutableLiveData<Long> = MutableLiveData()

    fun insert(score: Score) = viewModelScope.launch(Dispatchers.IO) {
        statusRegister.postValue(scoreRepository.insert(score))
    }

}